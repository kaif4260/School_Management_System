package com.school.schoolmanagementsystem.section.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SectionRequestDTO {

    @NotBlank(message = "Section name is required")
    @Size(
            min = 1,
            max = 20,
            message = "Section name cannot exceed 20 characters"
    )
    private String name;

    @Size(
            max = 255,
            message = "Description cannot exceed 255 characters"
    )
    private String description;

    @NotNull(message = "Class ID is required")
    private Long classId;

    public SectionRequestDTO() {
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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
