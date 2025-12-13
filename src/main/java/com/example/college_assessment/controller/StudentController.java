package com.example.college_assessment.controller;

import com.example.college_assessment.model.Student;
import com.example.college_assessment.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody Student s) {
        return ResponseEntity.ok(service.register(s));
    }

    @GetMapping("/college/{collegeId}")
    public List<Student> byCollege(@PathVariable String collegeId) {
        return service.getByCollege(collegeId);
    }

    @GetMapping("/pending/{collegeId}")
    public List<Student> pending(@PathVariable String collegeId) {
        return service.getPending(collegeId);
    }

    @GetMapping("/approved/{collegeId}")
    public List<Student> approved(@PathVariable String collegeId) {
        return service.getApproved(collegeId);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Student> approve(@PathVariable String id) {
        return ResponseEntity.ok(service.approve(id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Student> reject(@PathVariable String id) {
        return ResponseEntity.ok(service.reject(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ BRANCH WISE TEACHERS (IMPORTANT)
    @GetMapping("/teachers-by-department/{email}")
    public ResponseEntity<?> getTeachers(@PathVariable String email) {
        return ResponseEntity.ok(service.getTeachersForStudent(email));
    }
}
