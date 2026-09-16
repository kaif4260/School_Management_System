package com.school.schoolmanagementsystem.fee;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "fee_payments")
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "fee_assignment_id",
            nullable = false
    )
    private FeeAssignment feeAssignment;

    @Column(
            name = "amount_paid",
            nullable = false
    )
    private Double amountPaid;

    @Column(
            name = "payment_date",
            nullable = false
    )
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_method",
            nullable = false,
            length = 30
    )
    private PaymentMethod paymentMethod;

    @Column(
            name = "transaction_reference",
            length = 100
    )
    private String transactionReference;

    @Column(
            name = "remarks",
            length = 255
    )
    private String remarks;


    public FeePayment() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public FeeAssignment getFeeAssignment() {
        return feeAssignment;
    }

    public void setFeeAssignment(
            FeeAssignment feeAssignment) {

        this.feeAssignment = feeAssignment;
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

    public void setPaymentDate(LocalDate paymentDate) {
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