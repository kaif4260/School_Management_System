package com.school.schoolmanagementsystem.examinationsubject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ExaminationSubjectRequestDTO {

    @NotNull(message = "Examination ID is required")
    private Long examinationId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Maximum marks are required")
    @Min(
            value = 1,
            message = "Maximum marks must be greater than 0"
    )
    private Integer maxMarks;

    @NotNull(message = "Pass marks are required")
    @Min(
            value = 0,
            message = "Pass marks cannot be negative"
    )
    private Integer passMarks;

    @NotNull(message = "Exam date is required")
    private LocalDate examDate;

    public ExaminationSubjectRequestDTO() {
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Long examinationId) {
        this.examinationId = examinationId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Integer getPassMarks() {
        return passMarks;
    }

    public void setPassMarks(Integer passMarks) {
        this.passMarks = passMarks;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
}