package com.example.eventmanagement.Service;

import javax.mail.*; // Javax Mail um Emails zu versenden
import javax.mail.internet.*;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

     // Diese Methode versucht eine E-Mail zu senden
     public void sendEmail(String email, String eventName) {
        // Versuchen, eine E-Mail zu senden
        try {
            // E-Mail-Verbindungseigenschaften einrichten
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true"); // Authentifizierung aktivieren
            properties.put("mail.smtp.starttls.enable", "true"); // TLS aktivieren
            properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP-Host (Gmail)
            properties.put("mail.smtp.port", "587"); // SMTP-Port (587 für TLS)
            

            // Session erstellen mit den festgelegten Eigenschaften und Authentifizierung
            Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
                // Authentifizierungsdetails für G-Mail
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("t98137870@gmail.com", "vrhl yugt bknh fgdo"); // Wegwerf Account zum Senden von Emails 
                }
            });
        
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("t98137870@gmail.com")); // Absenderadresse 
            message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(email) // Empfängeradresse
            );
            message.setSubject("Erfolgreiche Registrierung für das Event"); // Betreff der E-Mail
            message.setText("Sie haben sich für folgendes Event registriert: " + eventName); // Nachrichtentext
        
            // Email senden
            Transport.send(message);
            System.out.println("Email sent successfully to " + email); // Bestätigung, dass die E-Mail gesendet wurde
        
        }
     
        catch (MessagingException e) {
            // Fehlerbehandlung 
            e.printStackTrace(); // printet den Fehler der beim fehlgeschlagenen Senden auftritt und wo
        }
    }
}
