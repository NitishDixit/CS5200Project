/*
This class is the main server class, which interacts and serves the clients on the 
system
*/

package secureim.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import secureim.utils.CryptoHelper;

public class Server {

	public static void main(String[] args) {
		
		// Read default connection properties for server from properties file
		System.out.println("Reading configuration...");
		Properties prop = new Properties();
		try
		{
			FileInputStream fin = new FileInputStream("Server.properties");
			prop.load(fin);
		}
		catch(IOException e)
		{
			System.out.println("Error reading settings file.");
			
			System.exit(-1);
		}
		
		if(!(prop.containsKey("ServerPort") 
				&& prop.containsKey("ServerPubKeyFile") 
				&& prop.containsKey("ServerPrivKeyFile")))
		{
			System.out.println("Properties file does not contain server settings");
			System.exit(-1);
		}
		
		try{
			// Reading Server's asymmetric keys
			FileInputStream serPubKeyFile = new FileInputStream(new File(prop.getProperty("ServerPubKeyFile")));
			FileInputStream serPrivKeyFile = new FileInputStream(new File(prop.getProperty("ServerPrivKeyFile")));

			// Reading server's public key from file
			DataInputStream dis = new DataInputStream(serPubKeyFile);
			byte[] serPubKey = new byte[dis.available()];
			dis.readFully(serPubKey);
			dis.close();

			// Reading server's private key from file
			dis = new DataInputStream(serPrivKeyFile);
			byte[] serPrivKey = new byte[dis.available()];
			dis.readFully(serPrivKey);
			dis.close();
			
			// Read server's port number 
			int port = Integer.parseInt((String)prop.get("ServerPort"));
			
			// Initialize the server with the port number and server's asymetric keys as the parameters.
			System.out.print("Starting server... ");
			SIMServer serv = new SIMServer(port,serPrivKey,serPubKey);
			Thread serThread = new Thread(serv);
			serThread.start();
			System.out.println("DONE");
		}
		catch(Exception e){
			
		}
	}
}
