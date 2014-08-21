/*
This class establishes the sockets and starts the server.
Also, it creates the base for other server classes.
*/

package secureim.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SIMServer implements Runnable{
	boolean stop = false;
	int port;
	byte[] privKey, pubKey;
	
	// Parameterized constructor that initialize server with the port number and server's asymmetric keys.	
	public SIMServer(int port1, byte[] priv, byte[] pub)
	{
		this.port = port1;
		privKey = priv;
		pubKey = pub;
	}
	
	// Starting the server
	public void run()
	{
		ServerSocket sock = null;
		try
		{
			sock = new ServerSocket(port);
			sock.setPerformancePreferences(1, 0, 0);
		}
		catch(IOException e)
		{
			
		}
		
		while( !stop )
		{
			try
			{	
				//Accepting connection request from clients and starting the thread
				Socket sessionSocket = sock.accept();
				ServerThread t = new ServerThread(sessionSocket, privKey, pubKey);
				Thread sthread = new Thread(t);
				sthread.start();
			}
			catch(SecurityException e)
			{
				System.out.println("Too many connections from host, dropping connection");
			}
			catch(IOException e)
			{
				
			}
		}
	}
	
	// Stop the server
	public void stop()
	{
		stop = true;
	}
}
