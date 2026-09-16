package com.school.schoolmanagementsystem.fee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeePaymentRepository
        extends JpaRepository<FeePayment, Long> {

    List<FeePayment> findByFeeAssignmentId(
            Long feeAssignmentId
    );

    List<FeePayment> findByPaymentMethod(
            PaymentMethod paymentMethod
    );
}