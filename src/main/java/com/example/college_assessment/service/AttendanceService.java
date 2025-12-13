package com.example.college_assessment.service;

import com.example.college_assessment.model.Attendance;
import com.example.college_assessment.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    // ✅ Save list, but avoid duplicate for same student+date
    public void saveAttendance(List<Attendance> list) {
        for (Attendance a : list) {
            boolean exists = repo.findByStudentIdAndDate(a.getStudentId(), a.getDate()).isPresent();
            if (!exists) {
                repo.save(a);
            }
        }
    }

    public List<Attendance> getAllAttendance() {
        return repo.findAll();
    }

    public List<Attendance> getAttendanceByDate(String date) {
        return repo.findByDate(date);
    }
}
