package com.school.schoolmanagementsystem.enrollment;

import com.school.schoolmanagementsystem.enrollment.dto.EnrollmentRequestDTO;
import com.school.schoolmanagementsystem.enrollment.dto.EnrollmentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class StudentEnrollmentController {

    private final StudentEnrollmentService enrollmentService;

    public StudentEnrollmentController(
            StudentEnrollmentService enrollmentService) {

        this.enrollmentService = enrollmentService;
    }

    // ENROLL STUDENT
    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO>
    enrollStudent(
            @Valid @RequestBody
            EnrollmentRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        enrollmentService.enrollStudent(request)
                );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>>
    getAllEnrollments() {

        return ResponseEntity.ok(
                enrollmentService.getAllEnrollments()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO>
    getEnrollmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enrollmentService
                        .getEnrollmentById(id)
        );
    }

    // STUDENT HISTORY
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponseDTO>>
    getStudentEnrollments(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                enrollmentService
                        .getStudentEnrollments(studentId)
        );
    }

    // CLASS STUDENTS
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<EnrollmentResponseDTO>>
    getClassEnrollments(
            @PathVariable Long classId) {

        return ResponseEntity.ok(
                enrollmentService
                        .getClassEnrollments(classId)
        );
    }

    // SECTION STUDENTS
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<EnrollmentResponseDTO>>
    getSectionEnrollments(
            @PathVariable Long sectionId) {

        return ResponseEntity.ok(
                enrollmentService
                        .getSectionEnrollments(sectionId)
        );
    }

    // ACADEMIC YEAR
    @GetMapping("/academic-year/{academicYear}")
    public ResponseEntity<List<EnrollmentResponseDTO>>
    getEnrollmentsByAcademicYear(
            @PathVariable String academicYear) {

        return ResponseEntity.ok(
                enrollmentService
                        .getEnrollmentsByAcademicYear(
                                academicYear
                        )
        );
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<EnrollmentResponseDTO>
    updateEnrollmentStatus(
            @PathVariable Long id,
            @RequestParam EnrollmentStatus status) {

        return ResponseEntity.ok(
                enrollmentService
                        .updateEnrollmentStatus(
                                id,
                                status
                        )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteEnrollment(
            @PathVariable Long id) {

        enrollmentService.deleteEnrollment(id);

        return ResponseEntity.noContent().build();
    }
}
