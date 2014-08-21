/*
This class helps in reading the salt, salted password and even the username
stored in the file.
*/

package secureim.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.StringTokenizer;

public class FileWorker {
	String file;
	public FileWorker(){
		Properties prop = new Properties();
		try
		{
			FileInputStream fin = new FileInputStream("Server.properties");
			prop.load(fin);
			if(!prop.containsKey("UserFile")){
				System.out.println("ServerConf is missing user file info...");
				System.exit(-1);
			}
			file = prop.getProperty("UserFile");
		}
		catch(IOException e)
		{
			System.out.println("Error reading settings file.");
			
			System.exit(-1);
		}
		
	}
	
	/*
	This function reads the salted password from the file.
	It takes in the user as an input and returns the salted password as string
	*/
	public String readSaltedPass(String user){
		String saltedPass = "";
		try{
			FileInputStream fis = new FileInputStream(new File(file));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String l = br.readLine();
			while(l!=null){
				StringTokenizer t = new StringTokenizer(l,":");
				String text = t.nextToken();
				if(text.equals(user)){
					t.nextToken();
					saltedPass = t.nextToken();
				}
				l = br.readLine();
			}
			br.close();
		}catch(Exception e){
			
		}
		return saltedPass;
	}

	/*
	This function reads the salted from the file.
	It takes in the user as an input and returns the salt as string
	*/
	public String readSalt(String user){
		String salt = "";
		try{
			FileInputStream fis = new FileInputStream(new File(file));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String l = br.readLine();
			while(l!=null){
				StringTokenizer t = new StringTokenizer(l,":");
				String text = t.nextToken();
				if(text.equals(user)){
					salt = t.nextToken();
				}
				l = br.readLine();
			}
			br.close();
		}catch(Exception e){
			
		}
		return salt;
	}
	
	/*
	This function reads the user name from the file.
	It takes in the user as an input and returns the user name as string
	*/
	public String readUsername(String user){
		String name = "";
		try{
			FileInputStream fis = new FileInputStream(new File(file));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String l = br.readLine();
			while(l!=null){
				StringTokenizer t = new StringTokenizer(l,":");
				String text = t.nextToken();
				if(text.equals(user)){
					t.nextToken();t.nextToken();
					name = t.nextToken();
				}
				l = br.readLine();
			}
			br.close();
		}catch(Exception e){
			
		}
		return name;
	}
}
