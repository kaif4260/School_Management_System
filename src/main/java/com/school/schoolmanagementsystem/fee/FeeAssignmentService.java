package com.school.schoolmanagementsystem.fee;

import com.school.schoolmanagementsystem.enrollment.StudentEnrollment;
import com.school.schoolmanagementsystem.enrollment.StudentEnrollmentRepository;
import com.school.schoolmanagementsystem.fee.dto.FeeAssignmentRequestDTO;
import com.school.schoolmanagementsystem.fee.dto.FeeAssignmentResponseDTO;
import com.school.schoolmanagementsystem.fee.dto.FeeSummaryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeAssignmentService {

    private final FeeAssignmentRepository feeAssignmentRepository;

    private final StudentEnrollmentRepository
            studentEnrollmentRepository;

    private final FeePaymentRepository feePaymentRepository;

    public FeeAssignmentService(
            FeeAssignmentRepository feeAssignmentRepository,
            StudentEnrollmentRepository studentEnrollmentRepository,
            FeePaymentRepository feePaymentRepository) {

        this.feeAssignmentRepository =
                feeAssignmentRepository;

        this.studentEnrollmentRepository =
                studentEnrollmentRepository;

        this.feePaymentRepository = feePaymentRepository;
    }

    // CREATE

    public FeeAssignmentResponseDTO create(
            FeeAssignmentRequestDTO request) {

        StudentEnrollment enrollment =
                studentEnrollmentRepository
                        .findById(request.getEnrollmentId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Enrollment not found with id: "
                                                + request.getEnrollmentId()
                                )
                        );


        if (request.getAmount() == null
                || request.getAmount() <= 0) {

            throw new IllegalArgumentException(
                    "Fee amount must be greater than 0"
            );
        }


        if (request.getDueDate() == null) {

            throw new IllegalArgumentException(
                    "Due date is required"
            );
        }


        FeeAssignment feeAssignment =
                new FeeAssignment();

        feeAssignment.setEnrollment(enrollment);

        feeAssignment.setFeeType(
                request.getFeeType()
        );

        feeAssignment.setAmount(
                request.getAmount()
        );

        feeAssignment.setDueDate(
                request.getDueDate()
        );

        feeAssignment.setDescription(
                request.getDescription()
        );


        FeeAssignment saved =
                feeAssignmentRepository.save(
                        feeAssignment
                );


        return convertToResponse(saved);
    }

    // GET ALL

    public List<FeeAssignmentResponseDTO> getAll() {

        return feeAssignmentRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID

    public FeeAssignmentResponseDTO getById(
            Long id) {

        FeeAssignment feeAssignment =
                feeAssignmentRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fee assignment not found with id: "
                                                + id
                                )
                        );

        return convertToResponse(
                feeAssignment
        );
    }

    // GET BY ENROLLMENT

    public List<FeeAssignmentResponseDTO>
    getByEnrollment(Long enrollmentId) {

        if (!studentEnrollmentRepository
                .existsById(enrollmentId)) {

            throw new RuntimeException(
                    "Enrollment not found with id: "
                            + enrollmentId
            );
        }


        return feeAssignmentRepository
                .findByEnrollmentId(enrollmentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY FEE TYPE

    public List<FeeAssignmentResponseDTO>
    getByFeeType(FeeType feeType) {

        return feeAssignmentRepository
                .findByFeeType(feeType)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // CONVERT ENTITY → RESPONSE

    private FeeAssignmentResponseDTO
    convertToResponse(
            FeeAssignment feeAssignment) {

        StudentEnrollment enrollment =
                feeAssignment.getEnrollment();


        String studentName =
                enrollment
                        .getStudent()
                        .getFirstName()
                        + " "
                        + enrollment
                        .getStudent()
                        .getLastName();


        return new FeeAssignmentResponseDTO(

                feeAssignment.getId(),

                enrollment.getId(),

                enrollment
                        .getStudent()
                        .getId(),

                studentName,

                enrollment
                        .getSchoolClass()
                        .getId(),

                enrollment
                        .getSchoolClass()
                        .getName(),

                enrollment
                        .getSection()
                        .getId(),

                enrollment
                        .getSection()
                        .getName(),

                enrollment.getAcademicYear(),

                feeAssignment.getFeeType(),

                feeAssignment.getAmount(),

                feeAssignment.getDueDate(),

                feeAssignment.getDescription()
        );
    }

    public FeeSummaryResponseDTO getFeeSummary(
            Long enrollmentId) {

        StudentEnrollment enrollment =
                studentEnrollmentRepository
                        .findById(enrollmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Enrollment not found with id: "
                                                + enrollmentId
                                )
                        );


        List<FeeAssignment> assignments =
                feeAssignmentRepository
                        .findByEnrollmentId(enrollmentId);


        double totalAssigned = assignments
                .stream()
                .mapToDouble(FeeAssignment::getAmount)
                .sum();


        double totalPaid = 0.0;


        for (FeeAssignment assignment : assignments) {

            List<FeePayment> payments =
                    feePaymentRepository
                            .findByFeeAssignmentId(
                                    assignment.getId()
                            );

            totalPaid += payments
                    .stream()
                    .mapToDouble(
                            FeePayment::getAmountPaid
                    )
                    .sum();
        }


        double totalPending =
                totalAssigned - totalPaid;


        String status;

        if (totalPaid == 0) {

            status = "PENDING";

        } else if (totalPaid < totalAssigned) {

            status = "PARTIAL";

        } else {

            status = "PAID";
        }


        String studentName =
                enrollment
                        .getStudent()
                        .getFirstName()
                        + " "
                        + enrollment
                        .getStudent()
                        .getLastName();


        return new FeeSummaryResponseDTO(

                enrollment.getId(),

                enrollment
                        .getStudent()
                        .getId(),

                studentName,

                enrollment.getAcademicYear(),

                totalAssigned,

                totalPaid,

                totalPending,

                status
        );
    }
}