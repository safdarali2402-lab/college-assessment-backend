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

    // ➤ Add new Teacher (Pending by default)
    // ➤ Add new Teacher (Pending by default)
    public Teacher addTeacher(Teacher t) {
        if (repo.existsByEmail(t.getEmail())) {
            throw new RuntimeException("Teacher already exists!");
        }

        t.setActive(true);
        t.setApproved(false);
        t.setStatus("PENDING");

        t.setRole("TEACHER"); // 🔥 Correct Role Format

        t.setPassword("Teacher@123"); // Default password

        return repo.save(t);
    }


    // ➤ Pending Teachers of particular College
    public List<Teacher> getPendingTeachers(String collegeId) {
        return repo.findByCollegeIdAndApprovedFalse(collegeId);
    }

    // ➤ Approved Teachers list
    public List<Teacher> getApprovedTeachers(String collegeId) {
        return repo.findByCollegeIdAndApprovedTrue(collegeId);
    }

    // ➤ Approve Teacher
    public Teacher approveTeacher(String id) {
        Teacher t = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        t.setApproved(true);
        t.setActive(true);
        t.setStatus("APPROVED");

        return repo.save(t);
    }

    // ➤ Reject Teacher
    public Teacher rejectTeacher(String id) {
        Teacher t = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        t.setApproved(false);
        t.setActive(false);
        t.setStatus("REJECTED");

        return repo.save(t);
    }

    // ➤ Delete Teacher
    public void deleteTeacher(String id) {
        repo.deleteById(id);
    }
}
