package com.school.schoolmanagementsystem.security;

import com.school.schoolmanagementsystem.security.dto.AuthResponseDTO;
import com.school.schoolmanagementsystem.security.dto.RegisterRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.school.schoolmanagementsystem.security.dto.LoginRequestDTO;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public AuthResponseDTO register(
            RegisterRequestDTO request) {

        // Validate email
        if (request.getEmail() == null
                || request.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Email is required"
            );
        }


        // Validate password
        if (request.getPassword() == null
                || request.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "Password is required"
            );
        }


        // Validate role
//        if (request.getRole() == null) {
//
//            throw new IllegalArgumentException(
//                    "Role is required"
//            );
//        }


        // Check duplicate email
        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }


        // Create user
        User user = new User();

        user.setEmail(
                request.getEmail()
        );

        // IMPORTANT:
        // Store BCrypt hash, NOT plain password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(Role.STUDENT
        );

        user.setEnabled(true);


        User saved =
                userRepository.save(user);


        return new AuthResponseDTO(
                saved.getId(),
                saved.getEmail(),
                saved.getRole(),
                null
        );
    }

    public AuthResponseDTO login(
            LoginRequestDTO request) {

        // Validate email
        if (request.getEmail() == null
                || request.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        // Validate password
        if (request.getPassword() == null
                || request.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "Password is required"
            );
        }

        // Find user
        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid email or password"
                                )
                        );

        // Check account status
        if (!user.isEnabled()) {

            throw new IllegalArgumentException(
                    "User account is disabled"
            );
        }

        // Verify password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        // Generate JWT
        String token =
                jwtService.generateToken(user);

        return new AuthResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}