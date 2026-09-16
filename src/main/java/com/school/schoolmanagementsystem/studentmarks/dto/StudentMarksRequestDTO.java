package com.school.schoolmanagementsystem.studentmarks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StudentMarksRequestDTO {

    @NotNull(message = "Enrollment ID is required")
    private Long enrollmentId;

    @NotNull(message = "Examination subject ID is required")
    private Long examinationSubjectId;

    @NotNull(message = "Obtained marks are required")
    @Min(
            value = 0,
            message = "Obtained marks cannot be negative"
    )
    private Integer obtainedMarks;


    public StudentMarksRequestDTO() {
    }


    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }


    public Long getExaminationSubjectId() {
        return examinationSubjectId;
    }

    public void setExaminationSubjectId(
            Long examinationSubjectId) {

        this.examinationSubjectId =
                examinationSubjectId;
    }


    public Integer getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(
            Integer obtainedMarks) {

        this.obtainedMarks = obtainedMarks;
    }
}