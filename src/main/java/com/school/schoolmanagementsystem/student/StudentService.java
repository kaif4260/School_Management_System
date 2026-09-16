package com.school.schoolmanagementsystem.student;

import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.student.dto.StudentRequestDTO;
import com.school.schoolmanagementsystem.student.dto.StudentResponseDTO;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    public StudentResponseDTO createStudent(StudentRequestDTO request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Student with email " + request.getEmail() + " already exists"
            );
        }

        if (studentRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(
                    "Student with phone " + request.getPhone() + " already exists"
            );
        }

        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());

        Student savedStudent = studentRepository.save(student);

        return convertToResponse(savedStudent);
    }

    // GET ALL
    public List<StudentResponseDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public StudentResponseDTO getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + id
                        ));

        return convertToResponse(student);
    }

    // UPDATE
    public StudentResponseDTO updateStudent(
            Long id,
            StudentRequestDTO request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + id
                        ));

        if (studentRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {

            throw new DuplicateResourceException(
                    "Another student already uses email "
                            + request.getEmail()
            );
        }

        if (studentRepository.existsByPhoneAndIdNot(
                request.getPhone(), id)) {

            throw new DuplicateResourceException(
                    "Another student already uses phone "
                            + request.getPhone()
            );
        }

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());

        Student updatedStudent = studentRepository.save(student);

        return convertToResponse(updatedStudent);
    }

    // DELETE
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Student not found with id: " + id
            );
        }

        studentRepository.deleteById(id);
    }

    // SEARCH + PAGINATION
    public PageResponse<StudentResponseDTO> searchStudents(
            String name,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("firstName").ascending()
        );

        Page<Student> studentPage =
                studentRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                name,
                                name,
                                pageable
                        );

        List<StudentResponseDTO> students =
                studentPage.getContent()
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return new PageResponse<>(
                students,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isFirst(),
                studentPage.isLast()
        );
    }

    // FIND BY EMAIL
    public StudentResponseDTO getStudentByEmail(String email) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with email: " + email
                        ));

        return convertToResponse(student);
    }

    // FIND BY PHONE
    public StudentResponseDTO getStudentByPhone(String phone) {

        Student student = studentRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with phone: " + phone
                        ));

        return convertToResponse(student);
    }

    // ENTITY → DTO
    private StudentResponseDTO convertToResponse(Student student) {

        return new StudentResponseDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getAddress()
        );
    }
}