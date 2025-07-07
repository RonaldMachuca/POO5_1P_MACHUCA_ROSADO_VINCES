/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.git;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
/**
 *
 * @author user
 */
public class ServicioCorreo {
    public static void enviarCorreo(String destinatario, String asunto, String mensaje, String remitente, String clave) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(remitente, clave);
        }
    });

    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport.send(message);
        System.out.println("Correo enviado a " + destinatario);
    } catch (MessagingException e) {
        System.out.println("Error al enviar: " + e.getMessage());
    }
   }
}
