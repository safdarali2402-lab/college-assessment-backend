package com.example.college_assessment.service;

import com.example.college_assessment.model.Teacher;
import com.example.college_assessment.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repo;

    public TeacherService(TeacherRepository repo) {
        this.repo = repo;
    }

    public Teacher addTeacher(Teacher t) {
        if (repo.existsByEmail(t.getEmail())) {
            throw new RuntimeException("Teacher already exists!");
        }

        t.setActive(true);
        t.setApproved(false);
        t.setStatus("PENDING");
        t.setRole("TEACHER");
        t.setPassword("Teacher@123");

        return repo.save(t);
    }

    public List<Teacher> getPendingTeachers(String collegeId) {
        return repo.findByCollegeIdAndApprovedFalse(collegeId);
    }

    public List<Teacher> getApprovedTeachers(String collegeId) {
        return repo.findByCollegeIdAndApprovedTrue(collegeId);
    }

    public Teacher approveTeacher(String id) {
        Teacher t = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        t.setApproved(true);
        t.setActive(true);
        t.setStatus("APPROVED");

        return repo.save(t);
    }

    public Teacher rejectTeacher(String id) {
        Teacher t = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        t.setApproved(false);
        t.setActive(false);
        t.setStatus("REJECTED");

        return repo.save(t);
    }

    public void deleteTeacher(String id) {
        repo.deleteById(id);
    }
}
