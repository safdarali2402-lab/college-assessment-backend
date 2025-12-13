package com.example.college_assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceView {

    private String id;
    private String studentId;
    private String studentName;
    private String rollNo;
    private boolean present;
    private String date;
}
