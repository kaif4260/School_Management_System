package com.school.schoolmanagementsystem.subject;

import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.subject.dto.SubjectRequestDTO;
import com.school.schoolmanagementsystem.subject.dto.SubjectResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(
            SubjectService subjectService) {

        this.subjectService = subjectService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<SubjectResponseDTO> createSubject(
            @Valid @RequestBody SubjectRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        subjectService.createSubject(request)
                );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<SubjectResponseDTO>>
    getAllSubjects() {

        return ResponseEntity.ok(
                subjectService.getAllSubjects()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                subjectService.getSubjectById(id)
        );
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<PageResponse<SubjectResponseDTO>>
    searchSubjects(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                subjectService.searchSubjects(
                        name,
                        page,
                        size
                )
        );
    }

    // GET BY CODE
    @GetMapping("/code/{code}")
    public ResponseEntity<SubjectResponseDTO>
    getSubjectByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                subjectService.getSubjectByCode(code)
        );
    }

    // GET BY NAME
    @GetMapping("/name/{name}")
    public ResponseEntity<SubjectResponseDTO>
    getSubjectByName(
            @PathVariable String name) {

        return ResponseEntity.ok(
                subjectService.getSubjectByName(name)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(
            @PathVariable Long id,
            @Valid @RequestBody SubjectRequestDTO request) {

        return ResponseEntity.ok(
                subjectService.updateSubject(
                        id,
                        request
                )
        );
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<SubjectResponseDTO>
    updateSubjectStatus(
            @PathVariable Long id,
            @RequestParam SubjectStatus status) {

        return ResponseEntity.ok(
                subjectService.updateSubjectStatus(
                        id,
                        status
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable Long id) {

        subjectService.deleteSubject(id);

        return ResponseEntity.noContent().build();
    }
}