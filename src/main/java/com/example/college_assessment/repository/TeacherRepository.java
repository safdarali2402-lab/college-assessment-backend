package com.example.college_assessment.repository;

import com.example.college_assessment.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

    boolean existsByEmail(String email);

    Optional<Teacher> findByEmail(String email);

    // 🔹 College-wise Approved Teacher List
    List<Teacher> findByCollegeIdAndApprovedTrue(String collegeId);

    // 🔹 College-wise Pending Teachers List
    List<Teacher> findByCollegeIdAndApprovedFalse(String collegeId);

    // 🔹 Teacher Department Students ke liye (future use)
    List<Teacher> findByDepartmentAndApprovedTrue(String department);
}
