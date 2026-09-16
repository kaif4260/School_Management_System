package com.school.schoolmanagementsystem.fee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeeAssignmentRepository
        extends JpaRepository<FeeAssignment, Long> {

    List<FeeAssignment> findByEnrollmentId(
            Long enrollmentId
    );

    List<FeeAssignment> findByFeeType(
            FeeType feeType
    );

    List<FeeAssignment> findByEnrollmentIdAndFeeType(
            Long enrollmentId,
            FeeType feeType
    );
}