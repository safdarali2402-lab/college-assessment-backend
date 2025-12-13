package com.example.college_assessment.controller;

import com.example.college_assessment.model.User;
import com.example.college_assessment.repository.UserRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        u.setPassword(null); // never expose password
        return ResponseEntity.ok(u);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest req) {
        User u = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!u.getPassword().equals(req.getOldPassword())) {
            return ResponseEntity.badRequest().body("Old password incorrect");
        }

        u.setPassword(req.getNewPassword());
        userRepo.save(u);
        return ResponseEntity.ok("Password updated");
    }

    @Data
    public static class ChangePasswordRequest {
        private String username;
        private String oldPassword;
        private String newPassword;
    }
}
