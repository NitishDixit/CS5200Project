package bean;

import javax.mail.internet.AddressException;

import service.EmailSender;

public class SendActivationMailBean {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String SendActivationMail(){

			String to[] = {"jagadeeshdeepak@gmail.com","karthikkc87@gmail.com"};

			try {
				
				if(EmailSender.sendMail("myself.434@gmail.com", "MANUNITED@2012", "Congragulations ...!!! Your PFM System Account has been Activated", to)){
					this.setMessage("Message successfully sent to the user..!!!");
				}else{
					this.setMessage("Error while sending the mail");
				}

			} catch (AddressException e) {
				e.printStackTrace();
			}
			
			return this.getMessage();
	
	}

}
