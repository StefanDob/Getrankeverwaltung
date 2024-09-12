package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.dataModel.Account;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailjetEmailSender {

    private static String fromEmail = "dobreastefan68@gmail.com";

    // Static method to send an email
    public static void sendEmail(String toEmail, String subject, String body) {
        // Mailjet SMTP server details
        final String smtpHost = "in-v3.mailjet.com";
        final String smtpPort = "587";
        final String username = "fabf362e33fb05bdde42625c5175ba58";  // Mailjet API Key
        final String password = "2e005418c74bfce2e55eb71e71a4b6bb";  // Mailjet Secret Key

        // Set SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Authenticate with the Mailjet SMTP server
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send the email.");
        }
    }

    public static void sendEmailToAdmins(String subject, String body){
        for(Account account: AccountOperations.getAllAccounts()) {
            if (account.getStatus() == AccountStatus.ADMIN) {
                String start = "Dear Mr/Ms. " + account.getLastName() + " , \n";
                String end = "\nBest regards, \n" +
                        "The Webshop Team";
                MailjetEmailSender.sendEmail(account.getEmail(), subject, start + body + end);
            }
        }
    }
}

