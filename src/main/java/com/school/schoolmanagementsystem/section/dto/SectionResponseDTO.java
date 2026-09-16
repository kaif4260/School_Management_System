package com.school.schoolmanagementsystem.section.dto;

import com.school.schoolmanagementsystem.section.SectionStatus;

public class SectionResponseDTO {

    private Long id;
    private String name;
    private String description;
    private SectionStatus status;

    private Long classId;
    private String className;

    public SectionResponseDTO() {
    }

    public SectionResponseDTO(
            Long id,
            String name,
            String description,
            SectionStatus status,
            Long classId,
            String className) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.classId = classId;
        this.className = className;
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

    public SectionStatus getStatus() {
        return status;
    }

    public void setStatus(SectionStatus status) {
        this.status = status;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
