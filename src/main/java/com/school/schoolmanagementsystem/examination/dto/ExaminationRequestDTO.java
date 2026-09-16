package com.school.schoolmanagementsystem.examination.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ExaminationRequestDTO {

    @NotBlank(message = "Examination name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Examination name must be between 2 and 100 characters"
    )
    private String name;

    @Size(
            max = 255,
            message = "Description cannot exceed 255 characters"
    )
    private String description;

    @NotBlank(message = "Academic year is required")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}$",
            message = "Academic year must be in format YYYY-YY"
    )
    private String academicYear;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    public ExaminationRequestDTO() {
    }

    @AssertTrue(message = "End date must be on or after start date")
    public boolean isDateRangeValid() {

        if (startDate == null || endDate == null) {
            return true;
        }

        return !endDate.isBefore(startDate);
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
}
