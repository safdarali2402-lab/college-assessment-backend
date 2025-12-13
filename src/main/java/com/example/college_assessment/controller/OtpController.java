package com.example.college_assessment.controller;

import com.example.college_assessment.service.EmailService;
import com.example.college_assessment.service.OtpService;
import com.example.college_assessment.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    // 🔹 SEND OTP
    @PostMapping("/send")
    public String sendOtp(@RequestParam String email,
                          @RequestParam String mobile) {

        String emailOtp = otpService.generateOtp();
        String mobileOtp = otpService.generateOtp();

        otpService.saveOtp(email, mobile, emailOtp, mobileOtp);

        emailService.sendEmailOtp(email, emailOtp);
        smsService.sendMobileOtp(mobile, mobileOtp);

        return "OTP sent to Email & Mobile";
    }

    // 🔹 VERIFY OTP
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String emailOtp,
                            @RequestParam String mobileOtp) {

        boolean verified = otpService.verifyOtp(email, emailOtp, mobileOtp);

        return verified ? "OTP VERIFIED ✅" : "INVALID OTP ❌";
    }
}
