/*
This class helps in creating the Diffie-Hellman parts of the keys.
*/

package secureim.utils;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

public class DHHelper implements Serializable{

	private static final long serialVersionUID = 1L;

	PublicKey pubKey;
	PrivateKey privKey;
	DHParameterSpec spec;

	
	public DHHelper(){
		try{
			AlgorithmParameterGenerator servAPG = AlgorithmParameterGenerator.getInstance("DH");
			servAPG.init(1024);
			DHParameterSpec specs = servAPG.generateParameters().getParameterSpec(DHParameterSpec.class);
			specs.getP();
			specs.getG();
			KeyPairGenerator kg = KeyPairGenerator.getInstance("DH");
			kg.initialize(specs);
		    KeyPair pair = kg.generateKeyPair();	   
		    privKey = pair.getPrivate(); // Getting the private key
		    pubKey = pair.getPublic(); // Getting the public key
		    spec = specs;
		    
		}
		catch(Exception e){
			
		}
	}
	
	/*
	Constructor to generate the key pair.
	*/
	public DHHelper(DHParameterSpec spec) throws 
		NoSuchAlgorithmException, InvalidAlgorithmParameterException
	{
		KeyPairGenerator kg = KeyPairGenerator.getInstance("DH");
		kg.initialize(spec);
	    KeyPair pair = kg.generateKeyPair();

	    privKey = pair.getPrivate();
	    pubKey = pair.getPublic();
	}
	
	/*
	Function to generate the secret key.
	*/
	public SecretKey doFinal(PublicKey pubKey, String symAlg) throws 
		InvalidKeyException, IllegalStateException, NoSuchAlgorithmException
	{
		KeyAgreement ka = KeyAgreement.getInstance("DH");
	    ka.init(privKey);
	    ka.doPhase(pubKey, true);
	    return ka.generateSecret(symAlg);
	}
	
	// Function to return the public key.
	public PublicKey getPublicKey()
	{
		return pubKey;
	}
	
	// Function to return the DHParameterSpec.
	public DHParameterSpec getParameterSpec()
	{
		return spec;
	}
	
}
