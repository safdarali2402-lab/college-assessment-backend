package com.example.college_assessment.controller;

import com.example.college_assessment.model.*;
import com.example.college_assessment.repository.*;
import com.example.college_assessment.security.JwtUtil;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;
    private final CollegeRepository collegeRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepo,
                          TeacherRepository teacherRepo,
                          StudentRepository studentRepo,
                          CollegeRepository collegeRepo,
                          PasswordEncoder passwordEncoder) {

        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
        this.collegeRepo = collegeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /* =====================================================
       🔐 LOGIN API (COLLEGE / TEACHER / STUDENT / ADMIN)
       ===================================================== */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {

        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(403).body("Invalid Credentials");
        }

        String role = null;
        String collegeId = null;
        String collegeName = null;
        String studentId = null;
        String teacherId = null;

        // 1️⃣ SUPER_ADMIN / COLLEGE_ADMIN
        Optional<User> uOpt = userRepo.findByUsername(req.getUsername());
        if (uOpt.isPresent()) {
            User u = uOpt.get();
            role = u.getRole();
            collegeId = u.getCollegeId();

            if (collegeId != null) {
                College c = collegeRepo.findById(collegeId).orElse(null);
                if (c != null) collegeName = c.getCollegeName();
            }
        }

        // 2️⃣ TEACHER LOGIN
        if (role == null) {
            Optional<Teacher> tOpt = teacherRepo.findByEmail(req.getUsername());
            if (tOpt.isPresent()) {
                Teacher t = tOpt.get();
                role = "ROLE_TEACHER";
                teacherId = t.getId();
                collegeId = t.getCollegeId();
                collegeName = t.getCollegeName();
            }
        }

        // 3️⃣ STUDENT LOGIN
        if (role == null) {
            Optional<Student> sOpt = studentRepo.findByEmail(req.getUsername());
            if (sOpt.isPresent()) {
                Student s = sOpt.get();
                role = "ROLE_STUDENT";
                studentId = s.getId();
                collegeId = s.getCollegeId();
                collegeName = s.getCollegeName();
            }
        }

        if (role == null) {
            return ResponseEntity.status(403).body("User not found!");
        }

        String token = jwtUtil.generateToken(req.getUsername(), role, collegeId);

        AuthResponse resp = AuthResponse.builder()
                .token(token)
                .username(req.getUsername())
                .role(role)
                .collegeId(collegeId)
                .collegeName(collegeName)
                .studentId(studentId)
                .teacherId(teacherId)
                .build();

        return ResponseEntity.ok(resp);
    }


    /* ===================== DTOs ===================== */

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Data
    @Builder
    public static class AuthResponse {
        private String token;
        private String username;
        private String role;
        private String collegeId;
        private String collegeName;
        private String studentId;
        private String teacherId;
    }
}
