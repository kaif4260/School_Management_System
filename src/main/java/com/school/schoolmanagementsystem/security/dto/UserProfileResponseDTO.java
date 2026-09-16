package com.school.schoolmanagementsystem.security.dto;

import com.school.schoolmanagementsystem.security.Role;

public class UserProfileResponseDTO {

    private Long id;

    private String email;

    private Role role;

    public UserProfileResponseDTO() {
    }

    public UserProfileResponseDTO(
            Long id,
            String email,
            Role role) {

        this.id = id;
        this.email = email;
        this.role = role;
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
}