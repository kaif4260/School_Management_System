package com.school.schoolmanagementsystem.classmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClassRequestDTO {

    @NotBlank(message = "Class name is required")
    @Size(
            min = 2,
            max = 50,
            message = "Class name must be between 2 and 50 characters"
    )
    private String name;

    @Size(
            max = 255,
            message = "Description cannot exceed 255 characters"
    )
    private String description;

    public ClassRequestDTO() {
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
}
