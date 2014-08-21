package service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class EmailSender {

	public static boolean sendMail(String from, String password, String message, String to[]) throws AddressException{

		String host ="smtp.gmail.com";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", password);
		properties.put("mail.smtp.port", 587);
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties);

		MimeMessage mimeMessage = new MimeMessage(session);

		try{
			mimeMessage.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			for(int i=0; i<to.length; i++){
				toAddress[i]=new InternetAddress(to[i]);
			}

			for(int i=0;i<toAddress.length; i++){
				mimeMessage.addRecipient(RecipientType.TO, toAddress[i]);				
			}

			mimeMessage.setSubject("Welcome to PFM System:: User Acitvation Mail");
			mimeMessage.setText(message);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
			return true;

		}catch(MessagingException e){
			e.printStackTrace();
		}
		return false;
	}

}
