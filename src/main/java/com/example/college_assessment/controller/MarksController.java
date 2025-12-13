package com.example.college_assessment.controller;

import com.example.college_assessment.dto.MarksRequest;
import com.example.college_assessment.model.Marks;
import com.example.college_assessment.repository.MarksRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin("*")
public class MarksController {

    private final MarksRepository marksRepo;

    public MarksController(MarksRepository marksRepo) {
        this.marksRepo = marksRepo;
    }

    // ⭐ ADD MARKS
    @PostMapping("/add")
    public ResponseEntity<?> addMarks(@RequestBody MarksRequest req) {

        if (req.getMarks() < 0 || req.getMarks() > 100) {
            return ResponseEntity.badRequest().body("Marks must be between 0-100");
        }

        Marks m = new Marks();
        m.setStudentId(req.getStudentId());
        m.setTeacherEmail(req.getTeacherEmail());
        m.setSubject(req.getSubject());
        m.setMarks(req.getMarks());
        m.setDate(LocalDate.now().toString());

        marksRepo.save(m);

        return ResponseEntity.ok(m);
    }

    // ⭐ GET MARKS FOR STUDENT
    @GetMapping("/student/{studentId}")
    public List<Marks> getStudentMarks(@PathVariable String studentId) {
        return marksRepo.findByStudentId(studentId);
    }

    // ⭐ GET MARKS ADDED BY TEACHER
    @GetMapping("/teacher/{teacherEmail}")
    public List<Marks> getTeacherMarks(@PathVariable String teacherEmail) {
        return marksRepo.findByTeacherEmail(teacherEmail);
    }
}
