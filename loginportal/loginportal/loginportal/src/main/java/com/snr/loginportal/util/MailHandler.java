package com.snr.loginportal.util;

import com.snr.loginportal.service.implement.StudentRegistrationServiceImpl;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class MailHandler {

    private static JavaMailSender mailSender;

    @Autowired
    public MailHandler(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private static String mailId = "vijaysankher04@gmail.com";

    private static final Logger logger = LoggerFactory.getLogger(MailHandler.class);

    /*public void sendEmailWithAttachment(String recipientEmail, Byte[] image) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeBodyPart attachmentPart = new MimeBodyPart();
        InputStream pdfInputStream = new ByteArrayInputStream(pdfBytes);
        attachmentPart.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfInputStream, "application/pdf")));
        attachmentPart.setFileName("certificate.pdf");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientEmail);
        helper.setSubject("Certificate of appreciation");
        helper.setText("Dear Participant, \n\nThanks for making this event a great success, hope you have learned more. Please find your certificate attached. Happy Learning ✨.");
        helper.addAttachment("Certificate.pdf", pdfFile);
        mailSender.send(message);
    }*/

    public static void sendEmailWithAttachment(String recipientEmail, byte[] pdfBytes) throws Exception {

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new RuntimeException("Error: PDF byte array is empty! Cannot send email.");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        logger.info("The Input values  : ---- : {}", mailId);
        // Set email details
        helper.setFrom(mailId); // Ensure sender email is set
        helper.setTo(recipientEmail);
        helper.setSubject("Certificate of Appreciation");
        helper.setText("Dear Participant, \n\nThanks for making this event a great success. "
                + "Please find your certificate attached. Happy Learning ✨.");

        // Convert byte[] to a PDF attachment
        ByteArrayDataSource pdfDataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");

        // Attach the PDF to the email
        helper.addAttachment("Certificate.pdf", pdfDataSource);

        // Send email
        mailSender.send(message);
        System.out.println("Email sent successfully to " + recipientEmail);
    }

}
