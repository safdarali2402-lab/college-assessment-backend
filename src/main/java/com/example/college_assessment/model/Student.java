package com.example.college_assessment.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private String id;

    private String name;
    private String email;
    private String phone;
    private String semester;
    private String department;
    private String year;
    private String rollNo;

    private String collegeId;
    private String collegeName;

    private Boolean active;
    private Boolean approved;     // Default pending
    private String status;
    private String password; // for student login
    private String role;

// "PENDING", "APPROVED", "REJECTED"
}
