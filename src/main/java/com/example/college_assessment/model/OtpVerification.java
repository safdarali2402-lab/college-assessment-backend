package com.example.college_assessment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "otp_verification")
public class OtpVerification {

    @Id
    private String id;

    private String email;
    private String mobile;

    private String emailOtp;
    private String mobileOtp;

    private LocalDateTime expiryTime;

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmailOtp() { return emailOtp; }
    public void setEmailOtp(String emailOtp) { this.emailOtp = emailOtp; }

    public String getMobileOtp() { return mobileOtp; }
    public void setMobileOtp(String mobileOtp) { this.mobileOtp = mobileOtp; }

    public LocalDateTime getExpiryTime() { return expiryTime; }
    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
