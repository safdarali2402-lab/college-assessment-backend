package com.example.college_assessment.repository;

import com.example.college_assessment.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    boolean existsByEmail(String email);

    // Student Login ke liye zaroori
    Optional<Student> findByEmail(String email);

    // Approved students
    List<Student> findByCollegeIdAndApprovedTrue(String collegeId);

    // Pending students
    List<Student> findByCollegeIdAndApprovedFalse(String collegeId);

    // All students
    List<Student> findByCollegeId(String collegeId);

    // Teacher ke student fetch karne ke liye
    List<Student> findByDepartmentAndCollegeIdAndApprovedTrue(String department, String collegeId);
}
