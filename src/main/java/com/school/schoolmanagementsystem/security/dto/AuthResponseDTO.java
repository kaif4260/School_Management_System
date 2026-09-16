package com.school.schoolmanagementsystem.security.dto;

import com.school.schoolmanagementsystem.security.Role;

public class AuthResponseDTO {

    private Long id;

    private String email;

    private Role role;

    private String accessToken;

    private String tokenType;


    public AuthResponseDTO() {
    }


    public AuthResponseDTO(
            Long id,
            String email,
            Role role,
            String accessToken) {

        this.id = id;
        this.email = email;
        this.role = role;
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}