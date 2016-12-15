package utill;

import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import DB.DB;
import domain.Kunde;

public class Emailer {
	// SKAL HAVE EXTENSION: JAVAMAIL API FØR CLASS'EN VIRKER. Softwaren kan
	// downloades her:
	// https://java.net/projects/javamail/pages/Home#Download_JavaMail_Release
	// (Download den nyeste version)
	public static void main(String[] args) throws SQLException {
		
	}
	public void glemtkodemail(String email, String nyadgangskode)throws SQLException{

		DB db = new DB();

		  Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");  
		   
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication("lortebank@gmail.com","password332");//email og password for vores lortebank  
		   }  
		  });  

		try {
			Kunde idiot = db.mailtoKunde(email); //

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("lorteBank@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("kodeord");
			message.setText("Hi " + idiot.getNavn()
					+ "Your new Master Password (the one you need before you can reset your password)" + "\n is: "
					+ nyadgangskode/* dot krypteret */
					+ "\n this is an automatic email");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}