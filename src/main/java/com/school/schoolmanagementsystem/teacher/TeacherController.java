package com.school.schoolmanagementsystem.teacher;

import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.teacher.dto.TeacherRequestDTO;
import com.school.schoolmanagementsystem.teacher.dto.TeacherResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createTeacher(
            @Valid @RequestBody TeacherRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teacherService.createTeacher(request));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {

        return ResponseEntity.ok(
                teacherService.getAllTeachers()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                teacherService.getTeacherById(id)
        );
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<PageResponse<TeacherResponseDTO>>
    searchTeachers(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                teacherService.searchTeachers(
                        name,
                        page,
                        size
                )
        );
    }

    // FIND BY EMPLOYEE ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<TeacherResponseDTO>
    getTeacherByEmployeeId(
            @PathVariable String employeeId) {

        return ResponseEntity.ok(
                teacherService.getTeacherByEmployeeId(
                        employeeId
                )
        );
    }

    // FIND BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<TeacherResponseDTO>
    getTeacherByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                teacherService.getTeacherByEmail(email)
        );
    }

    // FIND BY PHONE
    @GetMapping("/phone/{phone}")
    public ResponseEntity<TeacherResponseDTO>
    getTeacherByPhone(
            @PathVariable String phone) {

        return ResponseEntity.ok(
                teacherService.getTeacherByPhone(phone)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherRequestDTO request) {

        return ResponseEntity.ok(
                teacherService.updateTeacher(
                        id,
                        request
                )
        );
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<TeacherResponseDTO>
    updateTeacherStatus(
            @PathVariable Long id,
            @RequestParam TeacherStatus status) {

        return ResponseEntity.ok(
                teacherService.updateTeacherStatus(
                        id,
                        status
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable Long id) {

        teacherService.deleteTeacher(id);

        return ResponseEntity.noContent().build();
    }
}
