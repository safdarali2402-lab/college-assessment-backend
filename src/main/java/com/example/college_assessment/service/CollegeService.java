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
    private final EmailService emailService;

    public CollegeService(CollegeRepository repo,
                          UserRepository userRepo,
                          EmailService emailService) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    // 🔹 REGISTER COLLEGE
    public College registerCollege(College college) {

        if (repo.existsByEmail(college.getEmail())) {
            throw new RuntimeException("College already registered using this email");
        }

        college.setApproved(false);
        college.setActive(false);
        college.setStatus("PENDING");

        College saved = repo.save(college);

        // 📧 Registration email
        emailService.sendCollegeRegistrationMail(
                saved.getEmail(),
                saved.getCollegeName()
        );

        return saved;
    }

    public List<College> getPendingColleges() {
        return repo.findByApprovedFalse();
    }

    // ✅ APPROVE COLLEGE
    public College approveCollege(String id) {

        College college = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("College Not Found"));

        college.setApproved(true);
        college.setActive(true);
        college.setStatus("APPROVED");

        College saved = repo.save(college);

        // 📧 Approved email
        emailService.sendCollegeApprovedMail(
                saved.getEmail(),
                saved.getCollegeName()
        );

        // Auto-create College Admin User
        if (!userRepo.existsByUsername(college.getEmail())) {
            User admin = User.builder()
                    .username(college.getEmail())
                    .password("Temp@123")
                    .role("COLLEGE_ADMIN")
                    .collegeId(saved.getId())
                    .build();
            userRepo.save(admin);
        }

        return saved;
    }

    // ❌ REJECT COLLEGE
    public College rejectCollege(String id, String reason) {

        College college = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("College Not Found"));

        college.setApproved(false);
        college.setActive(false);
        college.setStatus("REJECTED");

        College saved = repo.save(college);

        // 📧 Rejected email
        emailService.sendCollegeRejectedMail(
                saved.getEmail(),
                saved.getCollegeName(),
                reason
        );

        return saved;
    }
}
