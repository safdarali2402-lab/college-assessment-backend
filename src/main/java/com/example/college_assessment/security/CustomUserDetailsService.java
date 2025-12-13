package com.example.college_assessment.security;

import com.example.college_assessment.model.Student;
import com.example.college_assessment.model.Teacher;
import com.example.college_assessment.model.User;
import com.example.college_assessment.repository.StudentRepository;
import com.example.college_assessment.repository.TeacherRepository;
import com.example.college_assessment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // ADMIN (SUPER ADMIN / COLLEGE ADMIN)
        User u = userRepo.findByUsername(username).orElse(null);
        if (u != null) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(u.getUsername())
                    .password(u.getPassword())
                    .authorities(u.getRole())  // FULL ROLE
                    .build();
        }

        // TEACHER
        Teacher t = teacherRepo.findByEmail(username).orElse(null);
        if (t != null) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(t.getEmail())
                    .password(t.getPassword())
                    .authorities("ROLE_TEACHER")
                    .build();
        }

        // STUDENT
        Student s = studentRepo.findByEmail(username).orElse(null);
        if (s != null) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(s.getEmail())
                    .password(s.getPassword())  // MUST MATCH EXACTLY
                    .authorities("ROLE_STUDENT")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }

}
