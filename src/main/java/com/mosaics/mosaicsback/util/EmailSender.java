package com.mosaics.mosaicsback.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    private static final Logger logger = LogManager.getLogger(EmailSender.class);

    private final static String FROM = "mosaic.software.ua@gmail.com";

    private final static String PASSWORD = "utpoydsavhkcyqwv";

    private final static String HOST = "smtp.gmail.com";

    public static void sendEmail(String recipientEmail) {

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.checkserveridentity", true);


        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(FROM, PASSWORD);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setFileName("Marker");
            mimeBodyPart.attachFile(new File(EmailSender.class.getResource("/HIRO.jpeg").getFile()));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(FROM));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject("Marker for AR");

            // Now set the actual message
            message.setText("Download this image and use navigate It on web-camera for viewing AR models");

            message.setContent(multipart);

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            logger.error("Cannot send email!", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
