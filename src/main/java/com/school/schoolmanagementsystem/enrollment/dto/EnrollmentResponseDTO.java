package com.school.schoolmanagementsystem.enrollment.dto;

import com.school.schoolmanagementsystem.enrollment.EnrollmentStatus;

public class EnrollmentResponseDTO {

    private Long id;

    private Long studentId;
    private Long classId;
    private Long sectionId;

    private String academicYear;

    private EnrollmentStatus status;

    public EnrollmentResponseDTO() {
    }

    public EnrollmentResponseDTO(
            Long id,
            Long studentId,
            Long classId,
            Long sectionId,
            String academicYear,
            EnrollmentStatus status) {

        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.sectionId = sectionId;
        this.academicYear = academicYear;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
}
