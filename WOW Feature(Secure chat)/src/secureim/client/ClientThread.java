/*
This class creates the threads for the clients
*/

package secureim.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

import secureim.utils.CryptoHelper;
import secureim.utils.DHHelper;
import secureim.utils.HashHelper;

public class ClientThread implements Runnable{
	SIMClient client;
	
	static final int MESSAGE = 4;
	
	ServerSocket servSock;
	
	public ClientThread(SIMClient client)
	{
		this.client = client;
	}
	
	public ServerSocket initSocket() throws IOException
	{
		servSock = new ServerSocket(0);
		return servSock;
	}
	
	@Override
	public void run() {
		
		while(!client.logout)
		{
			try
			{
				Socket sock = servSock.accept();
				if( !sock.isConnected() )
					continue;
				
				DataInputStream sin = new DataInputStream(sock.getInputStream());
				OutputStream sout = sock.getOutputStream();
				
				// Receive ttB, g^a modp and KAB{chat_msg,A,N1} from A
				
				// Reading ttB
				int len = HashHelper.ReadInt(sin);
				byte[] ttB = new byte[len];
				sin.readFully(ttB);
				
				// Decrypting and getting KAB,NB,A from ttB
				CryptoHelper ch = new CryptoHelper();
				
				// Decrypt the message
				ByteArrayInputStream bais = new ByteArrayInputStream(ttB);
				DataInputStream dis2 = new DataInputStream(bais);
				byte[] iv = new byte[HashHelper.ReadInt(dis2)];
				dis2.readFully(iv);
				byte[] msgen = new byte[HashHelper.ReadInt(dis2)]; 
				dis2.readFully(msgen);
				byte[] ttBdec = ch.symDecrypt(client.symK, iv, msgen);

				// Separate the ttb into it's corresponding parts
				ByteArrayInputStream in = new ByteArrayInputStream(ttBdec); 
				ObjectInputStream deserializer = new ObjectInputStream(in);
				SecretKey KAB = (SecretKey) deserializer.readObject();
				byte[] NB = new byte[32];
				deserializer.read(NB);
				String userA = (String) deserializer.readObject();
				
				// Reading g^a modp
				len = HashHelper.ReadInt(sin);
				byte[] gmmodp = new byte[len];
				sin.readFully(gmmodp);
				
				// Get the parameters and create new symmetric key for A and B
				in = new ByteArrayInputStream(gmmodp);
				deserializer = new ObjectInputStream(in);
				BigInteger p = (BigInteger) deserializer.readObject();
				BigInteger g = (BigInteger) deserializer.readObject();
				int L = deserializer.readInt();
				PublicKey pubKeyA = (PublicKey) deserializer.readObject();
				
				// Reading KAB{chat_msg,A,N1}
				len = HashHelper.ReadInt(sin);
				byte[] msgiv = new byte[len];
				sin.readFully(msgiv);
				
				// Decrypting the message
				bais = new ByteArrayInputStream(msgiv);
				dis2 = new DataInputStream(bais);
				iv = new byte[HashHelper.ReadInt(dis2)];
				dis2.readFully(iv);
				msgen = new byte[HashHelper.ReadInt(dis2)]; 
				dis2.readFully(msgen);
				byte[] msgdec = ch.symDecrypt(KAB, iv, msgen);
				
				// Separate chat_msg,A,N1
				in = new ByteArrayInputStream(msgdec);
				int chat_msg = in.read();
				
				if (chat_msg!=MESSAGE){
					System.out.println("Invalid request");
					continue;
				}
				byte[] userFromByte = new byte[HashHelper.ReadInt(in)];
				in.read(userFromByte);
				String userFrom = new String(userFromByte);
				byte[] N1 = new byte[32];
				in.read(N1);
				
				if(!userFrom.equals(userA)){
					System.out.println("invalid message received");
					continue;
				}
				
				// Generate KABN
				DHParameterSpec spec = new DHParameterSpec(p, g, L);
				DHHelper dh = new DHHelper(spec);
				PublicKey pubKeyB = dh.getPublicKey();
				SecretKey KABN = dh.doFinal(pubKeyA, "AES"); 
				
				// Send g^b modp and KAB{N1,N2}
				SecureRandom rand = new SecureRandom();
				byte[] N2 = new byte[32];
				rand.nextBytes(N2);
				
				// Encrpyting N1 and N2
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				out.write(N1);
				out.write(N2);
				byte[] encmsg = ch.symEncrypt(KAB, out.toByteArray());
				
				out = new ByteArrayOutputStream();
				ObjectOutputStream serializer = new ObjectOutputStream(out);
				serializer.writeObject(pubKeyB);
				serializer.flush();
				
				HashHelper.WriteInt(out,encmsg.length);
				out.write(encmsg);
				
				// Sending g^b modp and KAB{N1,N2}
				sout.write(out.toByteArray());
				
				// Receiving KAB{N2}
				len = HashHelper.ReadInt(sin);
				msgiv = new byte[len];
				sin.readFully(msgiv);
				
				// Decrypting the message
				bais = new ByteArrayInputStream(msgiv);
				dis2 = new DataInputStream(bais);
				iv = new byte[HashHelper.ReadInt(dis2)];
				dis2.readFully(iv);
				msgen = new byte[HashHelper.ReadInt(dis2)]; 
				dis2.readFully(msgen);
				byte[] N2_received = ch.symDecrypt(KAB, iv, msgen);
				
				// Validating N2
				if(!Arrays.equals(N2, N2_received)){
					System.out.println("N2 nonce is not equal...");
					continue;
				}
				
				// Receiving KABN{message}
				len = HashHelper.ReadInt(sin);
				msgiv = new byte[len];
				sin.readFully(msgiv);
				
				// Decrypting the message
				bais = new ByteArrayInputStream(msgiv);
				dis2 = new DataInputStream(bais);
				iv = new byte[HashHelper.ReadInt(dis2)];
				dis2.readFully(iv);
				msgen = new byte[HashHelper.ReadInt(dis2)]; 
				dis2.readFully(msgen);
				byte[] msgByte = ch.symDecrypt(KABN, iv, msgen);
				bais = new ByteArrayInputStream(msgByte);
				deserializer = new ObjectInputStream(bais);
				String msg = (String) deserializer.readObject();
				byte[] mac = new byte[HashHelper.ReadInt(bais)];
				bais.read(mac);
				MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
				byte[] mac_rec = msgDigest.digest(msg.getBytes());
				
				if(!Arrays.equals(mac_rec, mac)){
					System.out.println("Message integrity could not be verified");
					continue;
				}
				
				System.out.println("Message received from "+userA);
				System.out.println(msg);
				System.out.print(">");
			}
			catch(java.net.SocketException se){
				
			}
			catch(Exception e)
			{
				
			}
		}
	}

}
