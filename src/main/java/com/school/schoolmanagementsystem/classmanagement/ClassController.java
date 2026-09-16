package com.school.schoolmanagementsystem.classmanagement;

import com.school.schoolmanagementsystem.classmanagement.dto.ClassRequestDTO;
import com.school.schoolmanagementsystem.classmanagement.dto.ClassResponseDTO;
import com.school.schoolmanagementsystem.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ClassResponseDTO> createClass(
            @Valid @RequestBody ClassRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classService.createClass(request));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ClassResponseDTO>> getAllClasses() {

        return ResponseEntity.ok(
                classService.getAllClasses()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassResponseDTO> getClassById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                classService.getClassById(id)
        );
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<PageResponse<ClassResponseDTO>>
    searchClasses(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                classService.searchClasses(
                        name,
                        page,
                        size
                )
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ClassResponseDTO> updateClass(
            @PathVariable Long id,
            @Valid @RequestBody ClassRequestDTO request) {

        return ResponseEntity.ok(
                classService.updateClass(
                        id,
                        request
                )
        );
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClassResponseDTO>
    updateClassStatus(
            @PathVariable Long id,
            @RequestParam ClassStatus status) {

        return ResponseEntity.ok(
                classService.updateClassStatus(
                        id,
                        status
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(
            @PathVariable Long id) {

        classService.deleteClass(id);

        return ResponseEntity.noContent().build();
    }
}