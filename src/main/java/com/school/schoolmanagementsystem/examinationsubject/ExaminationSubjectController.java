package com.school.schoolmanagementsystem.examinationsubject;

import com.school.schoolmanagementsystem.examinationsubject.dto.ExaminationSubjectRequestDTO;
import com.school.schoolmanagementsystem.examinationsubject.dto.ExaminationSubjectResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examination-subjects")
public class ExaminationSubjectController {

    private final ExaminationSubjectService service;

    public ExaminationSubjectController(
            ExaminationSubjectService service) {

        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ExaminationSubjectResponseDTO>
    create(
            @Valid
            @RequestBody
            ExaminationSubjectRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ExaminationSubjectResponseDTO>>
    getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ExaminationSubjectResponseDTO>
    getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    // GET SUBJECTS FOR EXAM
    @GetMapping("/examination/{examinationId}")
    public ResponseEntity<List<ExaminationSubjectResponseDTO>>
    getByExamination(
            @PathVariable Long examinationId) {

        return ResponseEntity.ok(
                service.getByExamination(
                        examinationId
                )
        );
    }

    // GET EXAMS FOR SUBJECT
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<ExaminationSubjectResponseDTO>>
    getBySubject(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                service.getBySubject(
                        subjectId
                )
        );
    }

    // GET EXAMS FOR CLASS
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<ExaminationSubjectResponseDTO>>
    getByClass(
            @PathVariable Long classId) {

        return ResponseEntity.ok(
                service.getByClass(
                        classId
                )
        );
    }

    // GET SUBJECTS FOR EXAM + CLASS
    @GetMapping(
            "/examination/{examinationId}/class/{classId}"
    )
    public ResponseEntity<List<ExaminationSubjectResponseDTO>>
    getByExaminationAndClass(
            @PathVariable Long examinationId,
            @PathVariable Long classId) {

        return ResponseEntity.ok(
                service.getByExaminationAndClass(
                        examinationId,
                        classId
                )
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