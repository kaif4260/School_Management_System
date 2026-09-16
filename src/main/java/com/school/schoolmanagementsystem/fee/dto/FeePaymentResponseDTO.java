package com.school.schoolmanagementsystem.fee.dto;

import com.school.schoolmanagementsystem.fee.PaymentMethod;

import java.time.LocalDate;

public class FeePaymentResponseDTO {

    private Long id;

    private Long feeAssignmentId;

    private Long enrollmentId;

    private Long studentId;
    private String studentName;

    private String academicYear;

    private String feeType;

    private Double assignedAmount;

    private Double amountPaid;

    private Double totalPaid;

    private Double remainingAmount;

    private String feeStatus;

    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    private String transactionReference;

    private String remarks;


    public FeePaymentResponseDTO() {
    }


    public FeePaymentResponseDTO(
            Long id,
            Long feeAssignmentId,
            Long enrollmentId,
            Long studentId,
            String studentName,
            String academicYear,
            String feeType,
            Double assignedAmount,
            Double amountPaid,
            Double totalPaid,
            Double remainingAmount,
            String feeStatus,
            LocalDate paymentDate,
            PaymentMethod paymentMethod,
            String transactionReference,
            String remarks) {

        this.id = id;
        this.feeAssignmentId = feeAssignmentId;
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.academicYear = academicYear;
        this.feeType = feeType;
        this.assignedAmount = assignedAmount;
        this.amountPaid = amountPaid;
        this.totalPaid = totalPaid;
        this.remainingAmount = remainingAmount;
        this.feeStatus = feeStatus;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.transactionReference = transactionReference;
        this.remarks = remarks;
    }


    public Long getId() {
        return id;
    }

    public Long getFeeAssignmentId() {
        return feeAssignmentId;
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

    public String getFeeType() {
        return feeType;
    }

    public Double getAssignedAmount() {
        return assignedAmount;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public String getRemarks() {
        return remarks;
    }
}