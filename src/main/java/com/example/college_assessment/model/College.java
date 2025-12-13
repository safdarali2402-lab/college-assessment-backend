package com.example.college_assessment.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "colleges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class College {

    @Id
    private String id;

    private String collegeName;
    private String affiliationNo;
    private String principalName;
    private String email;
    private String phone;
    private String address;
    private String documentUrl;
    private Boolean approved ;
    private boolean active;
    private String status = "PENDING";

}
