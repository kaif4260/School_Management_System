package com.school.schoolmanagementsystem.security.dto;

//import com.school.schoolmanagementsystem.security.Role;

public class RegisterRequestDTO {

    private String email;

    private String password;

    //private Role role;


    public RegisterRequestDTO() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
}