package com.example.college_assessment.controller;

import com.example.college_assessment.model.Attendance;
import com.example.college_assessment.repository.AttendanceRepository;
import com.example.college_assessment.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;

    // ⭐ 1️⃣ Save attendance list (Teacher marks attendance)
    @PostMapping("/save")
    public ResponseEntity<?> saveAttendance(@RequestBody List<Attendance> list) {
        attendanceService.saveAttendance(list);
        return ResponseEntity.ok("Attendance Saved!");
    }

    // ⭐ 2️⃣ Fetch all attendance (admin)
    @GetMapping("/all")
    public List<Attendance> getAll() {
        return attendanceService.getAllAttendance();
    }

    // ⭐ 3️⃣ Fetch attendance by date (teacher/admin)
    @GetMapping("/date/{date}")
    public List<Attendance> getByDate(@PathVariable String date) {
        return attendanceService.getAttendanceByDate(date);
    }

    // ⭐ 4️⃣ FETCH STUDENT'S OWN ATTENDANCE (THIS IS WHAT YOU NEEDED)
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAttendanceByStudent(@PathVariable String studentId) {

        List<Attendance> records = attendanceRepository.findAll()
                .stream()
                .filter(a -> a.getStudentId().equals(studentId))   // Filter by studentId
                .toList();

        return ResponseEntity.ok(records);
    }
}
