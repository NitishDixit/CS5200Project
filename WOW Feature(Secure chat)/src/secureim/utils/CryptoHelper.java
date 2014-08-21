/*
This class helps in the Asymmetric and symmetric encoding and decoding of messages
Also, it helps in signing and verifying the signature for integrity checking.
*/

package secureim.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {
	
	public byte[] symEncrypt(SecretKey symKey, byte[] plainText){
		byte[] cipherText = null;
		
		ByteArrayOutputStream byteMsgArray = new ByteArrayOutputStream();
		try{
			
			// Create AES Cipher in CBC mode with PKCS5 Padding 
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,symKey,cipher.getParameters());
			// Encrypt the Data
			cipherText = cipher.doFinal(plainText);
			
			// Get the Initialization vector to pass with message
			byte[] iv = cipher.getIV();
			
			HashHelper.WriteInt(byteMsgArray, iv.length);
			byteMsgArray.write(iv);
			HashHelper.WriteInt(byteMsgArray, cipherText.length);
			byteMsgArray.write(cipherText);
			
			
		}
			
		catch (Exception e)
		{
			
		}
		return byteMsgArray.toByteArray();
	}
	
	/*
	This function does the asymmetric encryption on plain message.
	It takes in the public key and the plain text and returns the 
	encoded byte array
	*/
	public byte[] asymEncrypt(byte[] pubKey, byte[] plainText){
		byte[] enc = null;
		try{
			
			// Convert the encoded key from file into a real RSA public key.
			X509EncodedKeySpec destKeySpec = new X509EncodedKeySpec(pubKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(destKeySpec);

			// Create cipher from the public key
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
			rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			enc = rsaCipher.doFinal(plainText);
		}catch(Exception e){
			
		}
		return enc;
	}
	
	/*
	This function helps in signing the message for integrity checking.
	It takes byte array and key as an input and returns the signed byte array
	*/
	public byte[] sign(byte[] inp, byte[] key){
		byte[] signedMsg = null;
		try{
			// Convert the encoded key from file into RSA private key
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(key);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);

			// Create a Digital Signature to sign the data
			Signature digiSign = Signature.getInstance("SHA1withRSA");
			digiSign.initSign(privKey);
			
			// Signing the encrypted message
			digiSign.update(inp);
			signedMsg = digiSign.sign();
			
			
		
		}catch(Exception e){
			
			System.exit(-1);
		}
		return signedMsg;
	}
	
	/*
	This function helps in symmetric decryption of encoded message.
	It takes in the sym key, IV and the message as input and returns
	the decrypted byte data
	*/
	public byte[] symDecrypt(SecretKey symKey, byte[] iv, byte[] data){
		byte[] plainText = null;
		try{
			// Create AES Cipher in CBC mode with PKCS5 Padding 
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE,symKey,new IvParameterSpec(iv));
			
			// Decrypting the message
			plainText = cipher.doFinal(data);
		}catch(Exception e){
			
		}
		return plainText;
	}
	
	/*
	This function helps in asymmetric decryption of encoded message.
	It takes in the private key and the message and returns the decrypted
	byte array
	*/
	public byte[] asymDecrypt(byte[] privKey, byte[] inp){
		byte[] plain = null;
		try{
			// Convert the encoded key into a RSA private key.
		    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privKey);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		    
		    // Creating RSA cipher 
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
			rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			// RSA decrypts the symmetric key to get the actual AES key
			plain = rsaCipher.doFinal(inp);
		}
		catch(Exception e){
			
		}
		return plain;
	}

	/*
	This function helps in verifying the signature for the integrity checking 
	of the message.
	It takes in server public key, signed message and the data as an input and
	returns true if the message is verified else false.
	*/
	public boolean verifySign(byte[] serverPubKey, byte[] signedData, byte[] data){
		try{
			// Convert the encoded key into a RSA public key
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(serverPubKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

			// Creating the digital signature to verify the signed data
			Signature digiSign = Signature.getInstance("SHA1withRSA");
			digiSign.initVerify(pubKey);

			// Sign the encrypted data
			digiSign.update(data);
			return digiSign.verify(signedData);
			
		}catch(Exception e){
			
		}
		return false;
	}
	
	/*
	This function generates the symmetric secret key
	*/
	public SecretKey genSymKey(){
		try{
			// Generate AES symmetric key of 128 bits to encrypt data 
			KeyGenerator keyGenInst = KeyGenerator.getInstance("AES");
			keyGenInst.init(128);
			return keyGenInst.generateKey();
		}
		catch(NoSuchAlgorithmException e){
			System.out.println("Exception while trying to generate keys:");
			
		}
		return null;
	}
}
