package com.example.college_assessment.service;

import com.example.college_assessment.model.OtpVerification;
import com.example.college_assessment.repository.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpRepo;

    // 🔹 Generate 6-digit OTP
    public String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    // 🔹 Save OTP
    public void saveOtp(String email, String mobile,
                        String emailOtp, String mobileOtp) {

        OtpVerification otp = otpRepo.findByEmail(email)
                .orElse(new OtpVerification());

        otp.setEmail(email);
        otp.setMobile(mobile);
        otp.setEmailOtp(emailOtp);
        otp.setMobileOtp(mobileOtp);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(otp);
    }

    // ✅ THIS METHOD WAS MISSING (IMPORTANT)
    public boolean verifyOtp(String email,
                             String emailOtp,
                             String mobileOtp) {

        OtpVerification otp = otpRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("OTP not found"));

        // Expiry check
        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        // Match both OTPs
        return otp.getEmailOtp().equals(emailOtp)
                && otp.getMobileOtp().equals(mobileOtp);
    }
}
