/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Gabriel
 */
public class EMAIL extends Thread implements Serializable {

    final String fromEmail = "gabigrillo46@gmail.com"; //requires valid gmail id
    final String password = "tldpsjbibejppdsl"; // correct password for gmail id
    Session session;

    private String mensaje = "";
    private String emailDestino = "";
    private String subject = "";
    private String codigo ="";

    public EMAIL(String mensaje,String codigo,  String emailDestino, String subject) {
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        session = Session.getInstance(props, auth);
        this.mensaje = mensaje;
        this.emailDestino = emailDestino;
        this.subject = subject;
        this.codigo = codigo;
    }

    public void run() {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            //msg.setText(mensaje, "UTF-8");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino, false));

            String hmtl = "<html>"
                    + "<body>"
                    + "<h2>"+mensaje+"</h2>"
                    + "<h1 style=\"background-color:DodgerBlue;color:white\">"+codigo+"</h1>"
                    + "<h3> if you need more information please send us a email: <a href=\"mailto:gabigrillo46@gmail.com\">gabigrillo46@gmail.com</a> </h3>"
                    + "</body>"
                    + "</html>";
            
            
            
            


            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(hmtl, "text/html");

            mp.addBodyPart(htmlPart);
            msg.setContent(mp);

            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean enviarEmail(String mensaje, String emailDestino, String subject) {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(mensaje, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino, false));
            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
 // Create the message body part
         BodyPart messageBodyPart = new MimeBodyPart();

         messageBodyPart.setText(mensaje);
         
         // Create a multipart message for attachment
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Second part is image attachment
         messageBodyPart = new MimeBodyPart();
         String filename = "image.png";
         DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         //Trick is to add the content-id header here
         messageBodyPart.setHeader("Content-ID", "image_id");
         multipart.addBodyPart(messageBodyPart);

         //third part for displaying image in the email body
         messageBodyPart = new MimeBodyPart();
         messageBodyPart.setContent("<h1>Attached Image</h1>" +
        		     "<img src='cid:image_id'>", "text/html");
         multipart.addBodyPart(messageBodyPart);
         
         //Set the multipart message to the email message
         msg.setContent(multipart);

         // Send message
         Transport.send(msg);
         System.out.println("EMail Sent Successfully with image!!");
         return true;
      }catch (MessagingException e) {
         e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
		 e.printStackTrace();
	}*/
        return false;
    }
    
    
}
