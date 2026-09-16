package com.school.schoolmanagementsystem.fee;

import com.school.schoolmanagementsystem.fee.dto.FeeAssignmentRequestDTO;
import com.school.schoolmanagementsystem.fee.dto.FeeAssignmentResponseDTO;
import com.school.schoolmanagementsystem.fee.dto.FeeSummaryResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeAssignmentController {

    private final FeeAssignmentService feeAssignmentService;

    public FeeAssignmentController(
            FeeAssignmentService feeAssignmentService) {

        this.feeAssignmentService = feeAssignmentService;
    }


    // CREATE FEE ASSIGNMENT
    @PostMapping
    public FeeAssignmentResponseDTO create(
            @RequestBody FeeAssignmentRequestDTO request) {

        return feeAssignmentService.create(request);
    }


    // GET ALL FEE ASSIGNMENTS
    @GetMapping
    public List<FeeAssignmentResponseDTO> getAll() {

        return feeAssignmentService.getAll();
    }


    // GET FEE ASSIGNMENT BY ID
    @GetMapping("/{id}")
    public FeeAssignmentResponseDTO getById(
            @PathVariable Long id) {

        return feeAssignmentService.getById(id);
    }


    // GET FEES BY ENROLLMENT
    @GetMapping("/enrollment/{enrollmentId}")
    public List<FeeAssignmentResponseDTO> getByEnrollment(
            @PathVariable Long enrollmentId) {

        return feeAssignmentService
                .getByEnrollment(enrollmentId);
    }


    // GET FEES BY TYPE
    @GetMapping("/type/{feeType}")
    public List<FeeAssignmentResponseDTO> getByFeeType(
            @PathVariable FeeType feeType) {

        return feeAssignmentService
                .getByFeeType(feeType);
    }
    // SUMMARY
    @GetMapping("/summary/enrollment/{enrollmentId}")
    public FeeSummaryResponseDTO getFeeSummary(
            @PathVariable Long enrollmentId) {

        return feeAssignmentService
                .getFeeSummary(enrollmentId);
    }
}