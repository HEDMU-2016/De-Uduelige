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

public class Emailer{
// SKAL HAVE EXTENSION: JAVAMAIL API FØR CLASS'EN VIRKER. Softwaren kan downloades her:
//	https://java.net/projects/javamail/pages/Home#Download_JavaMail_Release
// (Download den nyeste version)	
    public static void glemtekodenMail(String email, String nyadgangskode) throws SQLException {
    	DB db = new DB();
 

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
        	Kunde idiot = db.mailtoKunde(email);
        	
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("LorteBank@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
            message.setSubject("kodeord");
            message.setText("Hi "+idiot.getNavn()
            	+ "Your new Master Password (the one you need before you can reset your password)"
                + "\n is: "+nyadgangskode/*dot krypteret*/
                + "\n this is an automatic email");
            
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}