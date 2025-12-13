package com.example.college_assessment.dto;

import lombok.Data;

@Data
public class MarksRequest {
    private String studentId;
    private String subject;
    private int marks;
    private String teacherEmail;
}
