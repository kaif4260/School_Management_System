package com.school.schoolmanagementsystem.fee;

import com.school.schoolmanagementsystem.fee.dto.FeePaymentRequestDTO;
import com.school.schoolmanagementsystem.fee.dto.FeePaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;

    private final FeeAssignmentRepository feeAssignmentRepository;


    public FeePaymentService(
            FeePaymentRepository feePaymentRepository,
            FeeAssignmentRepository feeAssignmentRepository) {

        this.feePaymentRepository =
                feePaymentRepository;

        this.feeAssignmentRepository =
                feeAssignmentRepository;
    }


    // =========================================================
    // CREATE PAYMENT
    // =========================================================

    public FeePaymentResponseDTO create(
            FeePaymentRequestDTO request) {

        FeeAssignment feeAssignment =
                feeAssignmentRepository
                        .findById(
                                request.getFeeAssignmentId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fee assignment not found with id: "
                                                + request
                                                .getFeeAssignmentId()
                                )
                        );


        // -----------------------------------------------------
        // Validate amount
        // -----------------------------------------------------

        if (request.getAmountPaid() == null
                || request.getAmountPaid() <= 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be greater than 0"
            );
        }


        // -----------------------------------------------------
        // Validate payment date
        // -----------------------------------------------------

        if (request.getPaymentDate() == null) {

            throw new IllegalArgumentException(
                    "Payment date is required"
            );
        }


        // -----------------------------------------------------
        // Calculate previous payments
        // -----------------------------------------------------

        List<FeePayment> previousPayments =
                feePaymentRepository
                        .findByFeeAssignmentId(
                                feeAssignment.getId()
                        );


        double totalPaid = previousPayments
                .stream()
                .mapToDouble(
                        FeePayment::getAmountPaid
                )
                .sum();


        // -----------------------------------------------------
        // Calculate remaining amount
        // -----------------------------------------------------

        double remainingAmount =
                feeAssignment.getAmount()
                        - totalPaid;


        // -----------------------------------------------------
        // Prevent overpayment
        // -----------------------------------------------------

        if (request.getAmountPaid()
                > remainingAmount) {

            throw new IllegalArgumentException(
                    "Payment amount exceeds remaining fee. "
                            + "Remaining amount: "
                            + remainingAmount
            );
        }


        // -----------------------------------------------------
        // Create payment
        // -----------------------------------------------------

        FeePayment payment =
                new FeePayment();

        payment.setFeeAssignment(
                feeAssignment
        );

        payment.setAmountPaid(
                request.getAmountPaid()
        );

        payment.setPaymentDate(
                request.getPaymentDate()
        );

        payment.setPaymentMethod(
                request.getPaymentMethod()
        );

        payment.setTransactionReference(
                request.getTransactionReference()
        );

        payment.setRemarks(
                request.getRemarks()
        );


        FeePayment saved =
                feePaymentRepository.save(
                        payment
                );


        return convertToResponse(saved);
    }


    // =========================================================
    // GET PAYMENT BY ID
    // =========================================================

    public FeePaymentResponseDTO getById(
            Long id) {

        FeePayment payment =
                feePaymentRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fee payment not found with id: "
                                                + id
                                )
                        );

        return convertToResponse(payment);
    }


    // =========================================================
    // GET ALL PAYMENTS
    // =========================================================

    public List<FeePaymentResponseDTO> getAll() {

        return feePaymentRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // =========================================================
    // GET PAYMENTS BY FEE ASSIGNMENT
    // =========================================================

    public List<FeePaymentResponseDTO>
    getByFeeAssignment(
            Long feeAssignmentId) {

        if (!feeAssignmentRepository
                .existsById(feeAssignmentId)) {

            throw new RuntimeException(
                    "Fee assignment not found with id: "
                            + feeAssignmentId
            );
        }


        return feePaymentRepository
                .findByFeeAssignmentId(
                        feeAssignmentId
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // =========================================================
    // CONVERT ENTITY → RESPONSE
    // =========================================================

    private FeePaymentResponseDTO
    convertToResponse(
            FeePayment payment) {

        FeeAssignment assignment =
                payment.getFeeAssignment();


        var enrollment =
                assignment.getEnrollment();


        String studentName =
                enrollment
                        .getStudent()
                        .getFirstName()
                        + " "
                        + enrollment
                        .getStudent()
                        .getLastName();


        // -----------------------------------------------------
        // Calculate total paid
        // -----------------------------------------------------

        double totalPaid =
                feePaymentRepository
                        .findByFeeAssignmentId(
                                assignment.getId()
                        )
                        .stream()
                        .mapToDouble(
                                FeePayment::getAmountPaid
                        )
                        .sum();


        double remainingAmount =
                assignment.getAmount()
                        - totalPaid;


        String feeStatus;


        if (totalPaid == 0) {

            feeStatus = "PENDING";

        } else if (totalPaid
                < assignment.getAmount()) {

            feeStatus = "PARTIAL";

        } else {

            feeStatus = "PAID";
        }


        return new FeePaymentResponseDTO(

                payment.getId(),

                assignment.getId(),

                enrollment.getId(),

                enrollment
                        .getStudent()
                        .getId(),

                studentName,

                enrollment.getAcademicYear(),

                assignment
                        .getFeeType()
                        .name(),

                assignment.getAmount(),

                payment.getAmountPaid(),

                totalPaid,

                remainingAmount,

                feeStatus,

                payment.getPaymentDate(),

                payment.getPaymentMethod(),

                payment.getTransactionReference(),

                payment.getRemarks()
        );
    }
}