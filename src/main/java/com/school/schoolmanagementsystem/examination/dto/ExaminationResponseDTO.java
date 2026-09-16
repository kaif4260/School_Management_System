package com.school.schoolmanagementsystem.examination.dto;

import com.school.schoolmanagementsystem.examination.ExaminationStatus;

import java.time.LocalDate;

public class ExaminationResponseDTO {

    private Long id;

    private String name;
    private String description;
    private String academicYear;

    private LocalDate startDate;
    private LocalDate endDate;

    private ExaminationStatus status;

    public ExaminationResponseDTO() {
    }

    public ExaminationResponseDTO(
            Long id,
            String name,
            String description,
            String academicYear,
            LocalDate startDate,
            LocalDate endDate,
            ExaminationStatus status) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.academicYear = academicYear;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ExaminationStatus getStatus() {
        return status;
    }

    public void setStatus(ExaminationStatus status) {
        this.status = status;
    }
}