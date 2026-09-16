package com.school.schoolmanagementsystem.fee.dto;

import com.school.schoolmanagementsystem.fee.PaymentMethod;

import java.time.LocalDate;

public class FeePaymentRequestDTO {

    private Long feeAssignmentId;

    private Double amountPaid;

    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    private String transactionReference;

    private String remarks;


    public FeePaymentRequestDTO() {
    }


    public Long getFeeAssignmentId() {
        return feeAssignmentId;
    }

    public void setFeeAssignmentId(
            Long feeAssignmentId) {

        this.feeAssignmentId = feeAssignmentId;
    }


    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }


    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(
            LocalDate paymentDate) {

        this.paymentDate = paymentDate;
    }


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(
            PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;
    }


    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(
            String transactionReference) {

        this.transactionReference =
                transactionReference;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}