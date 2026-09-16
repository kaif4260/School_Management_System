package com.school.schoolmanagementsystem.fee.dto;

public class FeeSummaryResponseDTO {

    private Long enrollmentId;

    private Long studentId;

    private String studentName;

    private String academicYear;

    private Double totalAssigned;

    private Double totalPaid;

    private Double totalPending;

    private String status;


    public FeeSummaryResponseDTO() {
    }


    public FeeSummaryResponseDTO(
            Long enrollmentId,
            Long studentId,
            String studentName,
            String academicYear,
            Double totalAssigned,
            Double totalPaid,
            Double totalPending,
            String status) {

        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.academicYear = academicYear;
        this.totalAssigned = totalAssigned;
        this.totalPaid = totalPaid;
        this.totalPending = totalPending;
        this.status = status;
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

    public String getAcademicYear() {
        return academicYear;
    }

    public Double getTotalAssigned() {
        return totalAssigned;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public Double getTotalPending() {
        return totalPending;
    }

    public String getStatus() {
        return status;
    }
}