package com.school.schoolmanagementsystem.fee.dto;

import com.school.schoolmanagementsystem.fee.FeeType;

import java.time.LocalDate;

public class FeeAssignmentRequestDTO {

    private Long enrollmentId;

    private FeeType feeType;

    private Double amount;

    private LocalDate dueDate;

    private String description;


    public FeeAssignmentRequestDTO() {
    }


    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }


    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}