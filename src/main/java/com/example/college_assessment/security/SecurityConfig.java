package com.example.college_assessment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth

                        // ⭐⭐⭐ VERY IMPORTANT (FIX) ⭐⭐⭐
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()

                        // PUBLIC APIs
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/colleges/register").permitAll()
                        .requestMatchers("/api/students/register").permitAll()
                        .requestMatchers("/api/student/**").permitAll()

                        // SUPER ADMIN
                        .requestMatchers(
                                "/api/colleges/pending",
                                "/api/colleges/approve/**"
                        ).hasAnyAuthority("ROLE_SUPER_ADMIN", "SUPER_ADMIN")

                        // STATS
                        .requestMatchers("/api/colleges/stats/**")
                        .hasAnyAuthority(
                                "ROLE_SUPER_ADMIN", "SUPER_ADMIN",
                                "ROLE_COLLEGE_ADMIN", "COLLEGE_ADMIN"
                        )

                        // COLLEGE ADMIN
                        .requestMatchers(
                                "/api/students/**",
                                "/api/teachers/add",
                                "/api/teachers/approve/**",
                                "/api/teachers/reject/**",
                                "/api/teachers/delete/**"
                        ).hasAnyAuthority("ROLE_COLLEGE_ADMIN", "COLLEGE_ADMIN")

                        // TEACHER
                        .requestMatchers(
                                "/api/teachers/dashboard/**",
                                "/api/teachers/students-by-department/**"
                        ).hasAnyAuthority("ROLE_TEACHER", "TEACHER")

                        // MARKS
                        .requestMatchers("/api/marks/add").hasAnyAuthority("ROLE_TEACHER", "TEACHER")
                        .requestMatchers("/api/marks/student/**").hasAnyAuthority("ROLE_STUDENT", "STUDENT")
                        .requestMatchers("/api/marks/teacher/**").hasAnyAuthority("ROLE_TEACHER", "TEACHER")

                        .anyRequest().authenticated()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
