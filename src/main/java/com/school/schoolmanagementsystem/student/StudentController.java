package com.school.schoolmanagementsystem.student;

import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.student.dto.StudentRequestDTO;
import com.school.schoolmanagementsystem.student.dto.StudentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.createStudent(request));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<StudentResponseDTO>> searchStudents(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                studentService.searchStudents(name, page, size)
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponseDTO> getStudentByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                studentService.getStudentByEmail(email)
        );
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<StudentResponseDTO> getStudentByPhone(
            @PathVariable String phone) {

        return ResponseEntity.ok(
                studentService.getStudentByPhone(phone)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                studentService.getStudentById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO request) {

        return ResponseEntity.ok(
                studentService.updateStudent(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

}