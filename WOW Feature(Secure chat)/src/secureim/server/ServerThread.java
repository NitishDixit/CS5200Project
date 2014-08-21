/*
This class instantiates the server thread that serves the client.
*/
package secureim.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

import secureim.utils.CryptoHelper;
import secureim.utils.DHHelper;
import secureim.utils.FileWorker;
import secureim.utils.HashHelper;

public class ServerThread implements Runnable{
	Socket sock;
	static List<UserInfo> loggedIn;
	byte[] privKey, pubKey;
	
	static final int LOGIN = 1;
	static final int LIST = 2;
	static final int LOGOUT = 3;
	static final int ERROR = 5;
	static final int CHALLENGE = 6;
	static final int KEY_REQ = 7;
	
	// Parameterized constructor with client socket and server's asymmetric keys as parameters	
	public ServerThread(Socket socket, byte[] priv, byte[] pub){
		this.sock = socket;
		privKey = priv;
		pubKey = pub;
	}
	
	public void run() {
		try {
			// Server will read the message from client
			DataInputStream dis = new DataInputStream(sock.getInputStream());
			int len = HashHelper.ReadInt(dis);
			byte[] msg = new byte[len];
			dis.readFully(msg);
			
			// Decrypt the message with server's private key
			CryptoHelper ch = new CryptoHelper();
			byte[] dec_Msg = ch.asymDecrypt(privKey, msg);
			//Retrieve the command from the message			
			int command = (int) dec_Msg[0];
			ByteArrayInputStream bin = new ByteArrayInputStream(Arrays.copyOfRange(dec_Msg, 1, dec_Msg.length));
			byte[] user = new byte[HashHelper.ReadInt(bin)];
			bin.read(user);
			// Retrive the port number from the message
			int portNo = HashHelper.ReadInt(bin); 
			
			// If command is LOGIN, server will authenticate the client by exchanging keys
			if(command==LOGIN){
				login(user,portNo);
				String name = "";

				while(command!=LOGOUT){
					// Read the message encrypted by symmetric key between client and server.
					len = HashHelper.ReadInt(dis);
					byte[] msgiv = new byte[len];
					dis.readFully(msgiv);

					// Decrypt the message with the symmetric key
					ByteArrayInputStream bais = new ByteArrayInputStream(msgiv);
					DataInputStream dis2 = new DataInputStream(bais);
					int len1 = HashHelper.ReadInt(dis2);
					byte[] iv = new byte[len1];
					dis2.readFully(iv);
					len1 = HashHelper.ReadInt(dis2);
					byte[] msg_en = new byte[len1]; 
					dis2.readFully(msg_en);
					byte[] msg1 = ch.symDecrypt(this.getKeyForUser(user), iv, msg_en);

					bais = new ByteArrayInputStream(msg1);
					dis2 = new DataInputStream(bais);
					command = HashHelper.ReadInt(dis2);
					
					switch(command){
					
					case LIST:
						list(this.getKeyForUser(user));
						break;
					
					case KEY_REQ:
						byte[] userB = new byte[dis2.available()];
						dis2.readFully(userB);
						name = new String(userB);
						sendttB(name,user);
						break;

					case LOGOUT:
						logout(user);
						break;
					}
				}
			}

		} 
		catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	//This function takes sender and the port number as input and login the user.
	public void login(byte[] user,int portNo){
		try{
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
			DataInputStream sin = new DataInputStream(sock.getInputStream());
			OutputStream sout = sock.getOutputStream();
			FileWorker fw = new FileWorker();
			String username = fw.readUsername(HashHelper.byteArrayToHexString(user));
			if ("".equals(username)){
				HashHelper.WriteInt(sout, ERROR);
				return;
			}
			// Check that user is not logged in already
			if(loggedIn==null){
				loggedIn = new ArrayList<UserInfo>();
			}
			for(UserInfo tempUser:loggedIn){
				if(username.equals(tempUser.username)){
					HashHelper.WriteInt(sout, ERROR);
					return;
				}
			}
			
			// Generate nonce
			SecureRandom random = new SecureRandom();
			byte nonce[] = new byte[32];
			random.nextBytes(nonce);
			
			// Read salt of password for the logged-in user
			String salt = fw.readSalt(HashHelper.byteArrayToHexString(user));
			if ("".equals(salt)){
				HashHelper.WriteInt(sout, ERROR);
				return;
			}
			byte[] saltByte = HashHelper.hexStringToByteArray(salt);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HashHelper.WriteInt(baos, nonce.length);
			baos.write(nonce);
			HashHelper.WriteInt(baos, saltByte.length);
			baos.write(saltByte);
			
			// Send nonce + salt
			sout.write(baos.toByteArray());
			
			// Receive {N, hash(salt||password), g^a mod p}ServerPublicKey
			byte[] msg2 = new byte[HashHelper.ReadInt(sin)];
			sin.readFully(msg2);
			
			// Decrypt the hashed salted password and Diffie-Hellman Key
			CryptoHelper ch = new CryptoHelper();
			byte[] msg = ch.asymDecrypt(privKey, msg2);
			ByteArrayInputStream bin = new ByteArrayInputStream(msg);
			DataInputStream din = new DataInputStream(bin);
			// Separate Nonce from data received 
			byte[] nonce_rec = new byte[HashHelper.ReadInt(din)];
			din.readFully(nonce_rec);
			
			// Compare nonces
			if(!Arrays.equals(nonce, nonce_rec)){
				System.out.println("incorrect nonce received from client");
				return;
			}
			
			// Separate salted hash of password from data received
			byte[] saltedHash = new byte[HashHelper.ReadInt(din)];
			din.readFully(saltedHash);
			
			// Compare hashes of passwords
			String pas = fw.readSaltedPass(HashHelper.byteArrayToHexString(user));
			byte[] saltedPass = HashHelper.hexStringToByteArray(pas);
			if(!Arrays.equals(saltedPass, saltedHash)){
				//System.out.println("Incorrect password");
				HashHelper.WriteInt(sout, ERROR);
				return;
				}
			
			// Read Diffie-Hellman part from data received
			ObjectInputStream deserializer = new ObjectInputStream(sin);
			BigInteger p = (BigInteger)deserializer.readObject();
			BigInteger g = (BigInteger)deserializer.readObject();
			int l = deserializer.readInt();
			DHParameterSpec spec = new DHParameterSpec(p, g, l);
			PublicKey pubkey = (PublicKey)deserializer.readObject();
			
			// Obtain symmetric Key and public key to send to client
			DHHelper vals = new DHHelper(spec);
			PublicKey pubKeyForClient = vals.getPublicKey();
			SecretKey symKey = vals.doFinal(pubkey, "AES");
			
			// Convert public Key into bytes
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream serializer = new ObjectOutputStream(out);
			serializer.writeObject(pubKeyForClient);
			serializer.flush();
			byte[] message = out.toByteArray();

			// Send Diffie-Hellman part of server to client
			out = new ByteArrayOutputStream();
			HashHelper.WriteInt(out, message.length);
			out.write(message);
			sout.write(out.toByteArray());
			
			// Send {C1}symKey
			out = new ByteArrayOutputStream();
			byte[] c1 = new byte[32];
			random.nextBytes(c1);
			byte[] c1enc = ch.symEncrypt(symKey, c1);
			HashHelper.WriteInt(out, c1enc.length);
			out.write(c1enc);
			sout.write(out.toByteArray());
			
			// Receive K{c1+c2} from client
			byte[] c1c2iv = new byte[HashHelper.ReadInt(sin)];
			sin.readFully(c1c2iv);
			ByteArrayInputStream bais = new ByteArrayInputStream(c1c2iv);
			DataInputStream dis = new DataInputStream(bais);
			int len = HashHelper.ReadInt(dis);
			byte[] iv = new byte[len];
			dis.readFully(iv);
			byte[] c1c2_enc = new byte[HashHelper.ReadInt(dis)]; 
			dis.readFully(c1c2_enc);
			byte[] c1c2 = ch.symDecrypt(symKey, iv, c1c2_enc);
			
			byte[] c1_rec = new byte[32];
			byte[] c2 = new byte[32];
			
			c1_rec = Arrays.copyOfRange(c1c2, 0, 32);
			c2 = Arrays.copyOfRange(c1c2, 32, c1c2.length);
			
			// Compare received c1 with generated c1
			if(!Arrays.equals(c1, c1_rec)){
				System.out.println("C1 Nonces not equal");
				return;
			}
			
			// Encrypt {c2}symKey
			byte[] c2_enc = ch.symEncrypt(symKey, c2);
			out = new ByteArrayOutputStream();
			HashHelper.WriteInt(out, c2_enc.length);
			out.write(c2_enc);
			sout.write(out.toByteArray());
			
			//Add user to logged in list
			UserInfo ui = new UserInfo();
			ui.username = username;
			ui.sessKey = symKey;
			ui.user = user;
			ui.port = portNo;
			ui.sock = sock.getLocalAddress();
			loggedIn.add(ui);
			System.out.println(ui.username + " authenticated and added");
		}
		catch(Exception e){
			
		}
	}
	
	//This function will return the list of online clients encrypted with the shared key between client and server.
	public void list(SecretKey k){
		try{
			// Encrypt and send list of online users
			OutputStream sout = sock.getOutputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream serializer = new ObjectOutputStream(out);
			Vector<String> userList = new Vector<String>();
			for(UserInfo u:loggedIn){
				userList.add(u.username);
			}
			serializer.writeObject(userList);
			serializer.flush();
			byte[] msg = out.toByteArray();
			CryptoHelper ch = new CryptoHelper();
			byte[] encmsg = ch.symEncrypt(k, msg); 
			out = new ByteArrayOutputStream();
			HashHelper.WriteInt(out, encmsg.length);
			out.write(encmsg);
			sout.write(out.toByteArray());
			
		}catch(Exception e){
			
		}
		
	}
	
	// This function logout the client and remove it from the online client list
	public void logout(byte[] usr){
		int i=0;
		for(UserInfo u:loggedIn){
			if(Arrays.equals(u.user,usr)){
				break;
			}
			i++;
		}
		System.out.println(loggedIn.get(i).username + " logged out");
		loggedIn.remove(i);
		
	}
	
	//Returns the secret key for the user send in the parameters.
	public SecretKey getKeyForUser(byte[] userA){
		for(UserInfo u:loggedIn){
			if(Arrays.equals(userA, u.user)){
				return u.sessKey;
			}
		}
		return null;
	}
	
	// Sends ticket to B ttb= KBS{KAB,NB,A}
	public void sendttB(String name, byte[] userFrom){
		try{
			DataInputStream sin = new DataInputStream(sock.getInputStream());
			OutputStream sout = sock.getOutputStream();
			InetAddress sockB = null;
			int portB = 0;

			// Get KBS for ticket-to-B
			SecretKey KBS = null;
			for(UserInfo u: loggedIn){
				if(name.equals(u.username)){
					KBS = u.sessKey;
					sockB = u.sock;
					portB = u.port;
				}
			}
			
			// Get sender's name
			String userA = "";
			for(UserInfo u: loggedIn){
				if(Arrays.equals(u.user,userFrom)){
					userA = u.username;
				}
			}
			
			// Get KAB for ticket-to-B and to send to A
			CryptoHelper ch = new CryptoHelper();
			SecretKey KAB = ch.genSymKey();

			// Create Nonce for B
			SecureRandom random = new SecureRandom();
			byte[] NB =  new byte[32];
			random.nextBytes(NB);
			
			// Create ttB = KBS{KAB,NB,A}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream serializer = new ObjectOutputStream(out);
			serializer.writeObject(KAB);
			serializer.write(NB);
			serializer.writeObject(userA);
			serializer.flush();
			byte[] ttB = ch.symEncrypt(KBS, out.toByteArray());
			
			// Create and send KAS{B,ttB,KAB} to A
			out = new ByteArrayOutputStream();
			serializer = new ObjectOutputStream(out);
			serializer.writeObject(name);
			serializer.writeObject(sockB);
			serializer.writeInt(portB);
			serializer.writeInt(ttB.length);
			serializer.write(ttB);
			serializer.writeObject(KAB);
			serializer.flush();
			byte[] reqKeyResp = ch.symEncrypt(this.getKeyForUser(userFrom), out.toByteArray());
			
			out = new ByteArrayOutputStream();
			HashHelper.WriteInt(out, reqKeyResp.length);
			out.write(reqKeyResp);
			sout.write(out.toByteArray());
			
		}catch(Exception e){
			
		}
	}
}


// Class userinfo contains the details of the logged in users.
class UserInfo{
	String username;
	SecretKey sessKey;
	byte[] user;
	InetAddress sock;
	int port;
}
