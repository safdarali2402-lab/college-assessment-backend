package com.example.college_assessment.service;

import com.example.college_assessment.model.Student;
import com.example.college_assessment.model.Teacher;
import com.example.college_assessment.repository.StudentRepository;
import com.example.college_assessment.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;
    private final TeacherRepository teacherRepo;

    public StudentService(StudentRepository repo,
                          TeacherRepository teacherRepo) {
        this.repo = repo;
        this.teacherRepo = teacherRepo;
    }

    // ✅ Student Register (Admin / Self both)
    public Student register(Student s) {

        if (repo.existsByEmail(s.getEmail())) {
            throw new RuntimeException("Student already exists!");
        }

        // Default ROLE for login system
        s.setRole("ROLE_STUDENT");

        // Default password if not provided
        if (s.getPassword() == null || s.getPassword().trim().isEmpty()) {
            s.setPassword("Student@123");
        }

        s.setActive(true);

        // Approval logic
        if (Boolean.TRUE.equals(s.getApproved())) {
            s.setApproved(true);
            s.setStatus("APPROVED");
        } else {
            s.setApproved(false);
            s.setStatus("PENDING");
        }

        return repo.save(s);
    }

    // ✅ Pending students
    public List<Student> getPending(String collegeId) {
        return repo.findByCollegeIdAndApprovedFalse(collegeId);
    }

    // ✅ Approved students
    public List<Student> getApproved(String collegeId) {
        return repo.findByCollegeIdAndApprovedTrue(collegeId);
    }

    // ✅ All students of college
    public List<Student> getByCollege(String collegeId) {
        return repo.findByCollegeId(collegeId);
    }

    // ✅ Approve student
    public Student approve(String id) {
        Student ss = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ss.setApproved(true);
        ss.setActive(true);
        ss.setStatus("APPROVED");
        return repo.save(ss);
    }

    // ✅ Reject student
    public Student reject(String id) {
        Student ss = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ss.setApproved(false);
        ss.setActive(false);
        ss.setStatus("REJECTED");
        return repo.save(ss);
    }

    // ✅ Delete student
    public void deleteStudent(String id) {
        repo.deleteById(id);
    }

    // =====================================================
    // ⭐ IMPORTANT: STUDENT → BRANCH WISE TEACHERS
    // =====================================================
    public List<Teacher> getTeachersForStudent(String email) {

        // 1️⃣ Get logged-in student
        Student s = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 2️⃣ Return teachers of SAME department + SAME college
        return teacherRepo.findByDepartmentAndCollegeIdAndApprovedTrue(
                s.getDepartment(),
                s.getCollegeId()
        );
    }
}
