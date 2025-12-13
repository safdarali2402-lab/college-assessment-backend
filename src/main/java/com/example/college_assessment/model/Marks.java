package com.example.college_assessment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "marks")
public class Marks {

    @Id
    private String id;

    private String studentId;
    private String teacherId;

    private String studentName;
    private String teacherName;
    private String teacherEmail; // teacher email stored

    private String subject;

    private int marks;            // ⭐ REQUIRED FIELD
    private int totalMarks;       // optional
    private String examType;      // mid, final, assignment

    private String date;
}
