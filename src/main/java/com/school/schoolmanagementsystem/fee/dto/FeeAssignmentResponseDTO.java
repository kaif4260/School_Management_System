package com.school.schoolmanagementsystem.fee.dto;

import com.school.schoolmanagementsystem.fee.FeeType;

import java.time.LocalDate;

public class FeeAssignmentResponseDTO {

    private Long id;

    private Long enrollmentId;

    private Long studentId;
    private String studentName;

    private Long classId;
    private String className;

    private Long sectionId;
    private String sectionName;

    private String academicYear;

    private FeeType feeType;

    private Double amount;

    private LocalDate dueDate;

    private String description;


    public FeeAssignmentResponseDTO() {
    }


    public FeeAssignmentResponseDTO(
            Long id,
            Long enrollmentId,
            Long studentId,
            String studentName,
            Long classId,
            String className,
            Long sectionId,
            String sectionName,
            String academicYear,
            FeeType feeType,
            Double amount,
            LocalDate dueDate,
            String description) {

        this.id = id;
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.classId = classId;
        this.className = className;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.academicYear = academicYear;
        this.feeType = feeType;
        this.amount = amount;
        this.dueDate = dueDate;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Long getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }
}