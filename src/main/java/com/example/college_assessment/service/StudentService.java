package com.example.college_assessment.service;

import com.example.college_assessment.model.Student;
import com.example.college_assessment.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // ✅ Student Register (Admin / Self both)
    public Student register(Student s) {

        if (repo.existsByEmail(s.getEmail())) {
            throw new RuntimeException("Student already exists!");
        }

        // Default ROLE for login system
        s.setRole("ROLE_STUDENT");   // correct format for Spring Security


        // Student must always have a password
        if (s.getPassword() == null || s.getPassword().trim().isEmpty()) {
            s.setPassword("Student@123");   // Default password first login
        }

        s.setActive(true);

        // If approved flag = true → directly approve
        if (Boolean.TRUE.equals(s.getApproved())) {
            s.setApproved(true);
            s.setStatus("APPROVED");
        } else {
            s.setApproved(false);
            s.setStatus("PENDING");
        }

        return repo.save(s);
    }

    // Pending
    public List<Student> getPending(String collegeId) {
        return repo.findByCollegeIdAndApprovedFalse(collegeId);
    }

    // Approved
    public List<Student> getApproved(String collegeId) {
        return repo.findByCollegeIdAndApprovedTrue(collegeId);
    }

    // All (Approved + Pending)
    public List<Student> getByCollege(String collegeId) {
        return repo.findByCollegeId(collegeId);
    }

    // Approve student
    public Student approve(String id) {
        Student ss = repo.findById(id).orElseThrow();
        ss.setApproved(true);
        ss.setActive(true);
        ss.setStatus("APPROVED");
        return repo.save(ss);
    }

    // Reject student
    public Student reject(String id) {
        Student ss = repo.findById(id).orElseThrow();
        ss.setApproved(false);
        ss.setActive(false);
        ss.setStatus("REJECTED");
        return repo.save(ss);
    }

    // Delete
    public void deleteStudent(String id) {
        repo.deleteById(id);
    }
}
