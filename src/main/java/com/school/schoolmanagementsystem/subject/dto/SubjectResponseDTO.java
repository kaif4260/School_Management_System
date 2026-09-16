package com.school.schoolmanagementsystem.subject.dto;

import com.school.schoolmanagementsystem.subject.SubjectStatus;

public class SubjectResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private SubjectStatus status;

    public SubjectResponseDTO() {
    }

    public SubjectResponseDTO(
            Long id,
            String code,
            String name,
            String description,
            SubjectStatus status) {

        this.id = id;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public SubjectStatus getStatus() {
        return status;
    }

    public void setStatus(SubjectStatus status) {
        this.status = status;
    }
}