package com.example.college_assessment.controller;

import com.example.college_assessment.model.Student;
import com.example.college_assessment.model.Teacher;
import com.example.college_assessment.repository.StudentRepository;
import com.example.college_assessment.repository.TeacherRepository;
import com.example.college_assessment.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:5173")
public class TeacherController {

    private final TeacherService service;
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;

    public TeacherController(TeacherService service,
                             TeacherRepository teacherRepo,
                             StudentRepository studentRepo) {
        this.service = service;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<Teacher> add(@RequestBody Teacher t) {
        return ResponseEntity.ok(service.addTeacher(t));
    }

    @GetMapping("/pending/{collegeId}")
    public List<Teacher> pending(@PathVariable String collegeId) {
        return service.getPendingTeachers(collegeId);
    }

    @GetMapping("/approved/{collegeId}")
    public List<Teacher> approved(@PathVariable String collegeId) {
        return service.getApprovedTeachers(collegeId);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Teacher> approve(@PathVariable String id) {
        return ResponseEntity.ok(service.approveTeacher(id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Teacher> reject(@PathVariable String id) {
        return ResponseEntity.ok(service.rejectTeacher(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    // ================= TEACHER DASHBOARD =================
    @GetMapping("/dashboard/{email}")
    public ResponseEntity<?> dashboard(@PathVariable String email) {
        Teacher t = teacherRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher Not Found"));
        return ResponseEntity.ok(t);
    }

    // ================= TEACHER → MY STUDENTS =================
    @GetMapping("/students-by-department/{email}")
    public ResponseEntity<?> getStudents(@PathVariable String email) {

        Teacher t = teacherRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher Not Found"));

        List<Student> students =
                studentRepo.findByDepartmentAndCollegeIdAndApprovedTrue(
                        t.getDepartment(),
                        t.getCollegeId()
                );

        return ResponseEntity.ok(students);
    }

    // ================= ⭐ STUDENT → MY TEACHERS (FIX) ⭐ =================
    @GetMapping("/by-student/{email}")
    public ResponseEntity<?> getTeachersForStudent(@PathVariable String email) {

        Student s = studentRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        List<Teacher> teachers =
                teacherRepo.findByDepartmentAndCollegeIdAndApprovedTrue(
                        s.getDepartment(),
                        s.getCollegeId()
                );

        return ResponseEntity.ok(teachers);
    }

    @PutMapping("/update-password/{email}")
    public ResponseEntity<?> updatePassword(@PathVariable String email,
                                            @RequestBody String newPass) {

        Teacher t = teacherRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher Not Found"));

        t.setPassword(newPass);
        teacherRepo.save(t);

        return ResponseEntity.ok("Password Updated Successfully!");
    }

    @GetMapping("/get-by-email/{email}")
    public Teacher getByEmail(@PathVariable String email) {
        return teacherRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }
}
