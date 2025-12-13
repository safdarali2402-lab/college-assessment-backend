package com.example.college_assessment.repository;

import com.example.college_assessment.model.Marks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends MongoRepository<Marks, String> {

    // Student marks fetch
    List<Marks> findByStudentId(String studentId);

    // Teacher ne jo marks diye
    List<Marks> findByTeacherId(String teacherId);

    // ⭐ Teacher email se marks fetch (controller me use ho raha)
    List<Marks> findByTeacherEmail(String teacherEmail);
}
