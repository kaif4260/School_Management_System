package com.school.schoolmanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // Disable CSRF because this is a REST API
                .csrf(csrf -> csrf.disable())
                //Enable CORS
                .cors(cors -> {})

                // JWT authentication is stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                // Handle unauthenticated requests
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(
                                        jwtAuthenticationEntryPoint
                                )
                                .accessDeniedHandler(
                                        jwtAccessDeniedHandler
                                )
                )

                .authorizeHttpRequests(auth -> auth

                        // Registration and login are public
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()
                        // Logged-in user only
                        .requestMatchers(
                                "/api/auth/me"
                        ).authenticated()

                        // ADMIN only
                        .requestMatchers(
                                "/api/security-test/admin"
                        ).hasRole("ADMIN")

                        // ADMIN + TEACHER
                        .requestMatchers(
                                "/api/security-test/teacher"
                        ).hasAnyRole("ADMIN", "TEACHER")

                        // ADMIN + STUDENT
                        .requestMatchers(
                                "/api/security-test/student"
                        ).hasAnyRole("ADMIN", "STUDENT")

                        // Any authenticated user
                        .requestMatchers(
                                "/api/security-test/all"
                        ).authenticated()

                        // Student APIs

                        // Only ADMIN can create students
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/students"
                        ).hasRole("ADMIN")

                        // ADMIN and TEACHER can view students
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/students/**"
                        ).hasAnyRole("ADMIN", "TEACHER")

                        // Only ADMIN can update students
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/students/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete students
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/students/**"
                        ).hasRole("ADMIN")


                        // Teacher APIs
                        // -----------------------------

                        // Only ADMIN can create teachers
                         .requestMatchers(
                            HttpMethod.POST,
                            "/api/teachers"
                        ).hasRole("ADMIN")

                        // ADMIN and TEACHER can view teachers
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/teachers/**"
                        ).hasAnyRole("ADMIN", "TEACHER")

                        // Only ADMIN can update teachers
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/teachers/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can update teacher status
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/teachers/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete teachers
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/teachers/**"
                        ).hasRole("ADMIN")

                        // Class APIs
                        // -----------------------------

                        // Only ADMIN can create classes
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/classes"
                        ).hasRole("ADMIN")

                        // ADMIN and TEACHER can view classes
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/classes/**"
                        ).hasAnyRole("ADMIN", "TEACHER")

                        // Only ADMIN can update classes
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/classes/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can update class status
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/classes/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete classes
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/classes/**"
                        ).hasRole("ADMIN")

                        // Section APIs
                        // -----------------------------

                        // Only ADMIN can create sections
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/sections"
                        ).hasRole("ADMIN")

                        // ADMIN and TEACHER can view sections
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/sections/**"
                        ).hasAnyRole("ADMIN", "TEACHER")

                        // Only ADMIN can update sections
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/sections/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can update section status
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/sections/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete sections
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/sections/**"
                        ).hasRole("ADMIN")


                        // Subject APIs
                        // -----------------------------

                        // Only ADMIN can create subjects
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/subjects"
                        ).hasRole("ADMIN")

                        // ADMIN and TEACHER can view subjects
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/subjects/**"
                        ).authenticated()

                        // Only ADMIN can update subjects
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/subjects/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can update subject status
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/subjects/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete subjects
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/subjects/**"
                        ).hasRole("ADMIN")

                        // Everything else requires authentication
                        .anyRequest()
                        .authenticated()
                )


                // Run JWT filter before Spring's
                // username/password filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}