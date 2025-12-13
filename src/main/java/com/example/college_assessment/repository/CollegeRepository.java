package com.example.college_assessment.repository;

import com.example.college_assessment.model.College;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollegeRepository extends MongoRepository<College, String> {
    boolean existsByEmail(String email);
    List<College> findByApprovedFalse();
}
