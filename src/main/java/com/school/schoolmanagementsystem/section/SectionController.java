package com.school.schoolmanagementsystem.section;

import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.section.dto.SectionRequestDTO;
import com.school.schoolmanagementsystem.section.dto.SectionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<SectionResponseDTO> createSection(
            @Valid @RequestBody SectionRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sectionService.createSection(request));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<SectionResponseDTO>>
    getAllSections() {

        return ResponseEntity.ok(
                sectionService.getAllSections()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SectionResponseDTO> getSectionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sectionService.getSectionById(id)
        );
    }

    // GET SECTIONS BY CLASS
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<SectionResponseDTO>>
    getSectionsByClass(
            @PathVariable Long classId) {

        return ResponseEntity.ok(
                sectionService.getSectionsByClass(classId)
        );
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<PageResponse<SectionResponseDTO>>
    searchSections(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                sectionService.searchSections(
                        name,
                        page,
                        size
                )
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SectionResponseDTO> updateSection(
            @PathVariable Long id,
            @Valid @RequestBody SectionRequestDTO request) {

        return ResponseEntity.ok(
                sectionService.updateSection(
                        id,
                        request
                )
        );
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<SectionResponseDTO>
    updateSectionStatus(
            @PathVariable Long id,
            @RequestParam SectionStatus status) {

        return ResponseEntity.ok(
                sectionService.updateSectionStatus(
                        id,
                        status
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(
            @PathVariable Long id) {

        sectionService.deleteSection(id);

        return ResponseEntity.noContent().build();
    }
}