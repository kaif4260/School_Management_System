package com.school.schoolmanagementsystem.classmanagement.dto;

import com.school.schoolmanagementsystem.classmanagement.ClassStatus;

public class ClassResponseDTO {

    private Long id;
    private String name;
    private String description;
    private ClassStatus status;

    public ClassResponseDTO() {
    }

    public ClassResponseDTO(
            Long id,
            String name,
            String description,
            ClassStatus status) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClassStatus getStatus() {
        return status;
    }

    public void setStatus(ClassStatus status) {
        this.status = status;
    }
}