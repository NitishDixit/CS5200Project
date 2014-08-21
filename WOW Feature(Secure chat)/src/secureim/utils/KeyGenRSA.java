/*
This class generates RSA keys and writes it onto the file.
*/

package secureim.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGenRSA {

	private byte[] publicKey;
	byte[] privateKey;
	String publicKeyFilename = "server_public.key";
    String privateKeyFilename = "server_private.key";
	
	// This function helps to generate the RSA keys
	private void generateKeys(){
		try{
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		    keyPairGenerator.initialize(1024);
		    KeyPair keyPair = keyPairGenerator.genKeyPair();
		    publicKey = keyPair.getPublic().getEncoded();
		    privateKey = keyPair.getPrivate().getEncoded();
		}catch(NoSuchAlgorithmException e){
			System.out.println("Exception while trying to init KeyGenerator:");
			
		}
			
	}
	
	// This function  helps to write the keys to file
	private void writeKeysToFiles(){
		try{
		    FileOutputStream fos = new FileOutputStream(publicKeyFilename);
			fos.write(publicKey);
			fos.close();
			fos = new FileOutputStream(privateKeyFilename);
			fos.write(privateKey);
			fos.close();
		}catch(IOException e){
			System.out.println("Exception while attempting to write to file:");
			
		}
		catch(Exception e){
			
		}
	}
	public static void main(String[] args) {
		KeyGenRSA keyGen = new KeyGenRSA();
		keyGen.generateKeys();
		keyGen.writeKeysToFiles();
	}
}
