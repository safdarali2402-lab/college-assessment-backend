package com.example.college_assessment.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    private String username;   // login email / username
    private String password;   // plain for now (later: encode)
    private String role;       // SUPER_ADMIN, COLLEGE_ADMIN, TEACHER, STUDENT

    private String collegeId;  // null for SUPER_ADMIN, set for COLLEGE_ADMIN
}
