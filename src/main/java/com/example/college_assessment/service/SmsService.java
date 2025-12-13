package com.example.college_assessment.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendMobileOtp(String mobile, String otp) {
        // 🔴 Production me Twilio / Fast2SMS use hoga
        System.out.println("📱 OTP sent to " + mobile + ": " + otp);
    }
}
