package com.school.schoolmanagementsystem.studentmarks;

import com.school.schoolmanagementsystem.studentmarks.dto.StudentMarksRequestDTO;
import com.school.schoolmanagementsystem.studentmarks.dto.StudentMarksResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/student-marks")
public class StudentMarksController {

    private final StudentMarksService service;

    public StudentMarksController(
            StudentMarksService service) {

        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<StudentMarksResponseDTO>
    create(
            @Valid
            @RequestBody
            StudentMarksRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<StudentMarksResponseDTO>>
    getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentMarksResponseDTO>
    getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    // GET BY ENROLLMENT

    @GetMapping("/enrollment/{enrollmentId}")
    public List<StudentMarksResponseDTO> getByEnrollment(
            @PathVariable Long enrollmentId) {

        return service.getByEnrollment(enrollmentId);
    }

    // GET ALL MARKS OF STUDENT
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentMarksResponseDTO>>
    getByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                service.getByStudent(studentId)
        );
    }

    // GET MARKS FOR EXAMINATION SUBJECT
    @GetMapping(
            "/examination-subject/{examinationSubjectId}"
    )
    public ResponseEntity<List<StudentMarksResponseDTO>>
    getByExaminationSubject(
            @PathVariable Long examinationSubjectId) {

        return ResponseEntity.ok(
                service.getByExaminationSubject(
                        examinationSubjectId
                )
        );
    }

    // GET MARKS FOR EXAMINATION
    @GetMapping("/examination/{examinationId}")
    public ResponseEntity<List<StudentMarksResponseDTO>>
    getByExamination(
            @PathVariable Long examinationId) {

        return ResponseEntity.ok(
                service.getByExamination(
                        examinationId
                )
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<StudentMarksResponseDTO>
    update(
            @PathVariable Long id,

            @Valid
            @RequestBody
            StudentMarksRequestDTO request) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
