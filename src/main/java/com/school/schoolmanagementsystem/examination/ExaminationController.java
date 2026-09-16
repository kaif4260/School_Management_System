package com.school.schoolmanagementsystem.examination;

import com.school.schoolmanagementsystem.examination.dto.ExaminationRequestDTO;
import com.school.schoolmanagementsystem.examination.dto.ExaminationResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examinations")
public class ExaminationController {

    private final ExaminationService examinationService;

    public ExaminationController(
            ExaminationService examinationService) {

        this.examinationService = examinationService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ExaminationResponseDTO>
    createExamination(
            @Valid
            @RequestBody
            ExaminationRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        examinationService
                                .createExamination(request)
                );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ExaminationResponseDTO>>
    getAllExaminations() {

        return ResponseEntity.ok(
                examinationService
                        .getAllExaminations()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ExaminationResponseDTO>
    getExaminationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                examinationService
                        .getExaminationById(id)
        );
    }

    // GET BY ACADEMIC YEAR
    @GetMapping("/academic-year/{academicYear}")
    public ResponseEntity<List<ExaminationResponseDTO>>
    getExaminationsByAcademicYear(
            @PathVariable String academicYear) {

        return ResponseEntity.ok(
                examinationService
                        .getExaminationsByAcademicYear(
                                academicYear
                        )
        );
    }

    // GET BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ExaminationResponseDTO>>
    getExaminationsByStatus(
            @PathVariable ExaminationStatus status) {

        return ResponseEntity.ok(
                examinationService
                        .getExaminationsByStatus(status)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ExaminationResponseDTO>
    updateExamination(
            @PathVariable Long id,
            @Valid
            @RequestBody
            ExaminationRequestDTO request) {

        return ResponseEntity.ok(
                examinationService.updateExamination(
                        id,
                        request
                )
        );
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<ExaminationResponseDTO>
    updateStatus(
            @PathVariable Long id,
            @RequestParam ExaminationStatus status) {

        return ResponseEntity.ok(
                examinationService.updateStatus(
                        id,
                        status
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteExamination(
            @PathVariable Long id) {

        examinationService.deleteExamination(id);

        return ResponseEntity.noContent().build();
    }
}
