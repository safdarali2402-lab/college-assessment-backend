package com.example.college_assessment.repository;

import com.example.college_assessment.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

    Optional<Teacher> findByEmail(String email);

    boolean existsByEmail(String email);

    // ✅ College-wise
    List<Teacher> findByCollegeIdAndApprovedFalse(String collegeId);

    List<Teacher> findByCollegeIdAndApprovedTrue(String collegeId);

    // ✅ ⭐ REQUIRED FOR BRANCH WISE LOGIC ⭐
    List<Teacher> findByDepartmentAndCollegeIdAndApprovedTrue(
            String department,
            String collegeId
    );
}
