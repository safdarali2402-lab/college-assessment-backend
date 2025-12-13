package com.example.college_assessment.repository;

import com.example.college_assessment.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    // Check if attendance already marked for student on a date
    Optional<Attendance> findByStudentIdAndDate(String studentId, String date);

    // List all attendance of a given date
    List<Attendance> findByDate(String date);
}
