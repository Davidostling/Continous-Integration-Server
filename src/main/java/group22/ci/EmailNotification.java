package group22.ci;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class to handle the email notification
 * Sends compile and test result of a build to a given email
 */
public class EmailNotification{
    public static boolean SendNotification(String to_email, String compileMessage, String testMessage, String testDetails, String branch) {

        // Recipient's email ID needs to be mentioned.
        String to = to_email;

        // Sender's email ID needs to be mentioned
        String from = "group22bot@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "imap.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("group22bot@gmail.com", "dd2480group22!");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Project: CI - Compile and test result for branch " + branch);

            // Now set the actual message
            message.setText(compileMessage + "\n" + testMessage + "\n" + testDetails);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

}
