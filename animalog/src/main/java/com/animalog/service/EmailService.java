package com.animalog.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${app.email.from:animalog@petcare.com}")
    private String fromEmail;
    
    // Send simple text email
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }
    
    // Send HTML email
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            System.out.println("✅ HTML Email sent successfully to: " + to);
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send HTML email: " + e.getMessage());
        }
    }
    
    // Send appointment confirmation email
    public void sendAppointmentConfirmation(String toEmail, String ownerName, String petName, 
                                           LocalDateTime appointmentDateTime, String purpose, 
                                           String veterinarianName) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' hh:mm a");
        String formattedDateTime = appointmentDateTime.format(formatter);
        
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #4CAF50, #45a049); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .details { background: white; padding: 20px; border-left: 4px solid #4CAF50; margin: 20px 0; }
                    .footer { text-align: center; color: #666; margin-top: 20px; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🐾 AnimaLog Pet Healthcare</h1>
                        <h2>Appointment Confirmed!</h2>
                    </div>
                    <div class="content">
                        <p>Dear %s,</p>
                        <p>Your appointment for <strong>%s</strong> has been successfully scheduled.</p>
                        
                        <div class="details">
                            <h3>📅 Appointment Details:</h3>
                            <p><strong>Pet:</strong> %s</p>
                            <p><strong>Date & Time:</strong> %s</p>
                            <p><strong>Purpose:</strong> %s</p>
                            <p><strong>Veterinarian:</strong> Dr. %s</p>
                        </div>
                        
                        <p>Please arrive 10 minutes early for check-in.</p>
                        <p>If you need to reschedule or cancel, please login to your account.</p>
                        
                        <div class="footer">
                            <p>This is an automated message from AnimaLog Pet Healthcare System</p>
                            <p>© 2024 AnimaLog. All rights reserved.</p>
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(ownerName, petName, petName, formattedDateTime, purpose, veterinarianName);
        
        sendHtmlEmail(toEmail, "Appointment Confirmation - AnimaLog", htmlContent);
    }
    
    // Send appointment cancellation email
    public void sendAppointmentCancellation(String toEmail, String ownerName, String petName, 
                                           LocalDateTime appointmentDateTime) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' hh:mm a");
        String formattedDateTime = appointmentDateTime.format(formatter);
        
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #e74c3c; color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .details { background: white; padding: 20px; border-left: 4px solid #e74c3c; margin: 20px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🐾 AnimaLog Pet Healthcare</h1>
                        <h2>Appointment Cancelled</h2>
                    </div>
                    <div class="content">
                        <p>Dear %s,</p>
                        <p>Your appointment for <strong>%s</strong> has been cancelled.</p>
                        
                        <div class="details">
                            <h3>📅 Cancelled Appointment:</h3>
                            <p><strong>Pet:</strong> %s</p>
                            <p><strong>Date & Time:</strong> %s</p>
                        </div>
                        
                        <p>You can book a new appointment anytime by logging into your account.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(ownerName, petName, petName, formattedDateTime);
        
        sendHtmlEmail(toEmail, "Appointment Cancelled - AnimaLog", htmlContent);
    }
    
    // Send welcome email
    public void sendWelcomeEmail(String toEmail, String userName, String role) {
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea, #764ba2); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🐾 Welcome to AnimaLog!</h1>
                    </div>
                    <div class="content">
                        <p>Dear %s,</p>
                        <p>Welcome to AnimaLog Pet Healthcare Management System!</p>
                        <p>Your account has been successfully created as a <strong>%s</strong>.</p>
                        <p>You can now login and start managing your pet healthcare needs.</p>
                        <p>Thank you for choosing AnimaLog!</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName, role);
        
        sendHtmlEmail(toEmail, "Welcome to AnimaLog Pet Healthcare", htmlContent);
    }
}