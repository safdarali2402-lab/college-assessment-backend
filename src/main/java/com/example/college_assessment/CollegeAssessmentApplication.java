package com.example.college_assessment;

import com.example.college_assessment.model.User;
import com.example.college_assessment.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CollegeAssessmentApplication {

	public static void main(String[] args) {

		SpringApplication.run(CollegeAssessmentApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository repo) {
		return args -> {
			if (!repo.existsByUsername("admin")) {
				User admin = User.builder()
						.username("admin")          // Govt Super Admin
						.password("Admin@700137")   // temporary
						.role("SUPER_ADMIN")
						.collegeId(null)
						.build();
				repo.save(admin);
				System.out.println("Super Admin Created ✔");
			}
		};
	}
}
