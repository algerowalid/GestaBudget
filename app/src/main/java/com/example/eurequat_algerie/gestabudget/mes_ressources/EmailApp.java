/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.eurequat_algerie.gestabudget.mes_ressources;

import com.example.innodev.easy_relay.MainActivity;

import java.io.IOException;
import java.util.Properties;

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

public class EmailApp {
    
    public static String Adress = "";
    public static String Titre= "";
    public static String MSG= "";


    public static void setter(String a, String t, String m)
    {
    EmailApp.Adress=a;
    EmailApp.Titre=t;
    EmailApp.MSG=m;
    }


    public static void main()throws IOException {
        final String username = MainActivity.MailAccount;
        final String password = MainActivity.MailPassword;


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(Adress));
            message.setSubject(Titre);
            message.setText(MSG);
       
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();

          /*  String attachmentPath = Main.Link+"src//document//";
            String attachmentName = "badge.pdf";
            File att = new File(new File(attachmentPath), attachmentName);
            messageBodyPart.attachFile(att);

            DataSource source = new FileDataSource(att);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
*/
            Transport.send(message);
           
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}