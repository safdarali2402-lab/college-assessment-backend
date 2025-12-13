package com.example.college_assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 🔹 OTP EMAIL
    public void sendEmailOtp(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP Verification – College Registration");
        message.setText(
                "Your Email OTP is: " + otp + "\n\n" +
                        "This OTP is valid for 5 minutes.\n\n" +
                        "Regards,\nStudent Assessment Tracker System"
        );

        mailSender.send(message);
        System.out.println("📧 OTP email sent to: " + to);
    }

    // 🔹 REGISTRATION RECEIVED EMAIL
    public void sendCollegeRegistrationMail(String to, String collegeName) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("College Registration Received – Student Assessment Tracker");

        message.setText(
                "Dear " + collegeName + ",\n\n" +
                        "Your college registration request has been received successfully.\n\n" +
                        "KYC verification will be conducted offline by our authorized agent.\n" +
                        "Our team will contact and visit your college shortly.\n\n" +
                        "Current Status: PENDING APPROVAL\n\n" +
                        "Regards,\nStudent Assessment Tracker Team"
        );

        mailSender.send(message);
        System.out.println("📧 Registration email sent to: " + to);
    }

    // ✅ COLLEGE APPROVED EMAIL
    public void sendCollegeApprovedMail(String to, String collegeName) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("College Approved – Student Assessment Tracker");

        message.setText(
                "Dear " + collegeName + ",\n\n" +
                        "Congratulations! 🎉\n\n" +
                        "Your college has been VERIFIED and APPROVED by the authorities.\n\n" +
                        "You can now log in and start using the system.\n\n" +
                        "Regards,\nStudent Assessment Tracker Team"
        );

        mailSender.send(message);
        System.out.println("📧 College APPROVED email sent to: " + to);
    }

    // ❌ COLLEGE REJECTED EMAIL
    public void sendCollegeRejectedMail(String to, String collegeName, String reason) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("College Registration Rejected");

        message.setText(
                "Dear " + collegeName + ",\n\n" +
                        "Your college registration request has been REJECTED.\n\n" +
                        "Reason: " + reason + "\n\n" +
                        "You may contact support or reapply with correct details.\n\n" +
                        "Regards,\nStudent Assessment Tracker Team"
        );

        mailSender.send(message);
        System.out.println("📧 College REJECTED email sent to: " + to);
    }
}
