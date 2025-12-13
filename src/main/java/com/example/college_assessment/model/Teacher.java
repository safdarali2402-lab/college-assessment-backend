package com.example.college_assessment.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    private String id;

    // Basic info
    private String name;
    private String email;
    private String phone;
    private String qualification;
    private String gender;

    // Department / Branch
    private String department;

    // Linked College
    private String collegeId;
    private String collegeName;

    // Login + Security
    private String password;
    private String role;   // ⭐ REQUIRED → e.g. "TEACHER"

    // Status fields
    private Boolean active;
    private Boolean approved;
    private String status;
}
