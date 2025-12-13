package com.example.college_assessment.controller;

import com.example.college_assessment.model.College;
import com.example.college_assessment.repository.StudentRepository;
import com.example.college_assessment.repository.TeacherRepository;
import com.example.college_assessment.service.CollegeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/colleges")
@CrossOrigin(origins = "http://localhost:5173")
public class CollegeController {

    private final CollegeService service;
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;

    public CollegeController(CollegeService service,
                             TeacherRepository teacherRepo,
                             StudentRepository studentRepo) {
        this.service = service;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }

    @PostMapping("/register")
    public College register(@RequestBody College college) {
        return service.registerCollege(college);
    }

    @GetMapping("/pending")
    public List<College> getPendingColleges() {
        return service.getPendingColleges();
    }

    @PutMapping("/approve/{id}")
    public College approveCollege(@PathVariable String id) {
        return service.approveCollege(id);
    }

    // ⭐ DASHBOARD STATS API (FIXED & WORKING)
    @GetMapping("/stats/{collegeId}")
    public ResponseEntity<?> getStats(@PathVariable String collegeId) {

        int approvedTeachers = teacherRepo.findByCollegeIdAndApprovedTrue(collegeId).size();
        int pendingTeachers = teacherRepo.findByCollegeIdAndApprovedFalse(collegeId).size();

        int approvedStudents = studentRepo.findByCollegeIdAndApprovedTrue(collegeId).size();
        int pendingStudents = studentRepo.findByCollegeIdAndApprovedFalse(collegeId).size();

        return ResponseEntity.ok(
                Map.of(
                        "teachers", approvedTeachers,
                        "students", approvedStudents,
                        "pendingTeachers", pendingTeachers,
                        "pendingStudents", pendingStudents
                )
        );
    }
}
