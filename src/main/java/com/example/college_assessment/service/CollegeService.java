package com.example.college_assessment.service;

import com.example.college_assessment.model.College;
import com.example.college_assessment.model.User;
import com.example.college_assessment.repository.CollegeRepository;
import com.example.college_assessment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeService {

    private final CollegeRepository repo;
    private final UserRepository userRepo;

    public CollegeService(CollegeRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public College registerCollege(College college) {
        if (repo.existsByEmail(college.getEmail())) {
            throw new RuntimeException("College already registered using this email");
        }
        college.setApproved(false);
        return repo.save(college);
    }

    public List<College> getPendingColleges() {
        return repo.findByApprovedFalse();
    }

    public College approveCollege(String id) {
        College college = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("College Not Found"));

        college.setApproved(true);
        College saved = repo.save(college);

        // Auto-create College Admin User (only if not already exists)
        if (!userRepo.existsByUsername(college.getEmail())) {
            User admin = User.builder()
                    .username(college.getEmail())   // login with college email
                    .password("Temp@123")           // default password
                    .role("COLLEGE_ADMIN")
                    .collegeId(saved.getId())
                    .build();

            userRepo.save(admin);
        }

        return saved;
    }
}
