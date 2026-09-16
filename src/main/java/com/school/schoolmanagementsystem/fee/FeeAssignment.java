package com.school.schoolmanagementsystem.fee;

import com.school.schoolmanagementsystem.enrollment.StudentEnrollment;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "fee_assignments"
)
public class FeeAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "enrollment_id",
            nullable = false
    )
    private StudentEnrollment enrollment;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "fee_type",
            nullable = false,
            length = 30
    )
    private FeeType feeType;

    @Column(
            name = "amount",
            nullable = false
    )
    private Double amount;

    @Column(
            name = "due_date",
            nullable = false
    )
    private LocalDate dueDate;

    @Column(
            name = "description",
            length = 255
    )
    private String description;


    public FeeAssignment() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public StudentEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(
            StudentEnrollment enrollment) {

        this.enrollment = enrollment;
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

    public void setDescription(
            String description) {

        this.description = description;
    }
}