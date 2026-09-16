package com.school.schoolmanagementsystem.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security-test")
public class SecurityTestController {

    @GetMapping("/admin")
    public String adminAccess() {
        return "ADMIN access granted";
    }

    @GetMapping("/teacher")
    public String teacherAccess() {
        return "TEACHER access granted";
    }

    @GetMapping("/student")
    public String studentAccess() {
        return "STUDENT access granted";
    }

    @GetMapping("/all")
    public String allAuthenticatedUsers() {
        return "Authenticated user access granted";
    }
}