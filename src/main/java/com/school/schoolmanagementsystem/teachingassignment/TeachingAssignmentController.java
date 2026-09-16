package com.school.schoolmanagementsystem.teachingassignment;

import com.school.schoolmanagementsystem.teachingassignment.dto.TeachingAssignmentRequestDTO;
import com.school.schoolmanagementsystem.teachingassignment.dto.TeachingAssignmentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaching-assignments")
public class TeachingAssignmentController {

    private final TeachingAssignmentService assignmentService;

    public TeachingAssignmentController(
            TeachingAssignmentService assignmentService) {

        this.assignmentService = assignmentService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TeachingAssignmentResponseDTO>
    createAssignment(
            @Valid @RequestBody
            TeachingAssignmentRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        assignmentService.createAssignment(request)
                );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<TeachingAssignmentResponseDTO>>
    getAllAssignments() {

        return ResponseEntity.ok(
                assignmentService.getAllAssignments()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TeachingAssignmentResponseDTO>
    getAssignmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assignmentService.getAssignmentById(id)
        );
    }

    // GET BY TEACHER
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>>
    getAssignmentsByTeacher(
            @PathVariable Long teacherId) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignmentsByTeacher(teacherId)
        );
    }

    // GET BY SUBJECT
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>>
    getAssignmentsBySubject(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignmentsBySubject(subjectId)
        );
    }

    // GET BY CLASS
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>>
    getAssignmentsByClass(
            @PathVariable Long classId) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignmentsByClass(classId)
        );
    }

    // GET BY SECTION
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>>
    getAssignmentsBySection(
            @PathVariable Long sectionId) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignmentsBySection(sectionId)
        );
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<TeachingAssignmentResponseDTO>
    updateAssignmentStatus(
            @PathVariable Long id,
            @RequestParam AssignmentStatus status) {

        return ResponseEntity.ok(
                assignmentService.updateAssignmentStatus(
                        id,
                        status
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long id) {

        assignmentService.deleteAssignment(id);

        return ResponseEntity.noContent().build();
    }
}