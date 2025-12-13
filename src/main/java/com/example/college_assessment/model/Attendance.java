package com.example.college_assessment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attendance")
@Data
public class Attendance {

    @Id
    private String id;
    private String studentId;
    private boolean present;
    private String date;
}
