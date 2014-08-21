/*
This class reads and write on to different output and input streams.
*/

package secureim.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashHelper {

	public static void main(String[] args) {
		String username = "";
		String password = "";

		try{
			// Salt is stored in a byte array
			SecureRandom random = new SecureRandom();
			byte salt[] = new byte[32];
			random.nextBytes(salt);
			MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");

			// Storing the username, Password and Salt in bytes
			byte[] usernameHash = msgDigest.digest(username.getBytes("UTF-16"));
			byte[] passwordByte = password.getBytes("UTF-16");
			byte[] saltedPassword = new byte[salt.length + passwordByte.length];
			System.arraycopy(salt, 0, saltedPassword, 0, salt.length);
			System.arraycopy(passwordByte, 0, saltedPassword, salt.length, passwordByte.length);
			byte[] saltedHash = msgDigest.digest(saltedPassword); 
			
			String temp="";
			// Converting the username to Hex
			temp = byteArrayToHexString(usernameHash);
			System.out.println("Username: " + temp);
			
			// Converting salt to Hex
			temp = byteArrayToHexString(salt);
			System.out.println("Salt: " + temp);
			
			// Converting the salted password to Hex
			temp = byteArrayToHexString(saltedHash);
			System.out.println("Salted Password: " + temp);
			
			
		}catch(Exception e){
			System.out.println("Exception while computing hash");
			
		}

	}
	
	// This function converts the byte array to hex string
	public static String byteArrayToHexString(byte[] b){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	// This function converts the hex string to byte array
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	// This function reads int from an inputstream
	static public int ReadInt(InputStream in) throws IOException
	{
		int one = in.read();
		int two = in.read();
		int three = in.read();
		int four = in.read();
		
		return (four << 24)
        + ((three & 0xFF) << 16)
        + ((two & 0xFF) << 8)
        + (one & 0xFF);
	}

	// This function writes num onto the outputstream
	static public void WriteInt(OutputStream out, int num) 
	{
		try{
		out.write(num);
		out.write(num >> 8);
		out.write(num >> 16);
		out.write(num >> 24);
		}
		catch(Exception e){}
	}
	
	// This function converts Bytes to Int
	static public int BytesToInt(byte[] b)
	{
		if( b.length != 4 )
			return 0;
		
		return (b[0] << 24)
        + ((b[1] & 0xFF) << 16)
        + ((b[2] & 0xFF) << 8)
        + (b[3] & 0xFF);
	}
	
	// This function converts Int to Bytes
	static public byte[] IntToBytes(int i)
	{
		return new byte[] {
                (byte)(i >>> 24),
                (byte)(i >>> 16),
                (byte)(i >>> 8),
                (byte)i};
	}

	
}
