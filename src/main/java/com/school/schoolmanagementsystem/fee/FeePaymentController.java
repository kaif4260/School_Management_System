package com.school.schoolmanagementsystem.fee;

import com.school.schoolmanagementsystem.fee.dto.FeePaymentRequestDTO;
import com.school.schoolmanagementsystem.fee.dto.FeePaymentResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fee-payments")
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    public FeePaymentController(
            FeePaymentService feePaymentService) {

        this.feePaymentService = feePaymentService;
    }


    // CREATE PAYMENT
    @PostMapping
    public FeePaymentResponseDTO create(
            @RequestBody FeePaymentRequestDTO request) {

        return feePaymentService.create(request);
    }


    // GET ALL PAYMENTS
    @GetMapping
    public List<FeePaymentResponseDTO> getAll() {

        return feePaymentService.getAll();
    }


    // GET PAYMENT BY ID
    @GetMapping("/{id}")
    public FeePaymentResponseDTO getById(
            @PathVariable Long id) {

        return feePaymentService.getById(id);
    }


    // GET PAYMENTS BY FEE ASSIGNMENT
    @GetMapping("/assignment/{feeAssignmentId}")
    public List<FeePaymentResponseDTO> getByFeeAssignment(
            @PathVariable Long feeAssignmentId) {

        return feePaymentService
                .getByFeeAssignment(feeAssignmentId);
    }
}