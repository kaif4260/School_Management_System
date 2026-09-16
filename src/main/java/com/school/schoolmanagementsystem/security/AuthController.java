package com.school.schoolmanagementsystem.security;

import com.school.schoolmanagementsystem.security.dto.AuthResponseDTO;
import com.school.schoolmanagementsystem.security.dto.LoginRequestDTO;
import com.school.schoolmanagementsystem.security.dto.RegisterRequestDTO;
import com.school.schoolmanagementsystem.security.dto.UserProfileResponseDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @GetMapping("/me")
    public UserProfileResponseDTO getCurrentUser(
            Authentication authentication) {

        User user =
                (User) authentication.getPrincipal();

        return new UserProfileResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }


    @PostMapping("/register")
    public AuthResponseDTO register(
            @RequestBody RegisterRequestDTO request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody LoginRequestDTO request) {

        return authService.login(request);
    }
}