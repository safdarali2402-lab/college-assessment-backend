package com.example.college_assessment.repository;

import com.example.college_assessment.model.OtpVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OtpVerificationRepository
        extends MongoRepository<OtpVerification, String> {

    Optional<OtpVerification> findByEmail(String email);
}
