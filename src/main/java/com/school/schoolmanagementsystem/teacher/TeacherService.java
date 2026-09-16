package com.school.schoolmanagementsystem.teacher;

import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.school.schoolmanagementsystem.teacher.dto.TeacherRequestDTO;
import com.school.schoolmanagementsystem.teacher.dto.TeacherResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // CREATE
    public TeacherResponseDTO createTeacher(
            TeacherRequestDTO request) {

        validateDuplicates(request, null);

        Teacher teacher = new Teacher();

        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        teacher.setQualification(request.getQualification());
        teacher.setSpecialization(request.getSpecialization());
        teacher.setJoiningDate(request.getJoiningDate());

        // New teachers are active by default
        teacher.setStatus(TeacherStatus.ACTIVE);

        Teacher savedTeacher =
                teacherRepository.save(teacher);

        return convertToResponse(savedTeacher);
    }

    // GET ALL
    public List<TeacherResponseDTO> getAllTeachers() {

        return teacherRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public TeacherResponseDTO getTeacherById(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher not found with id: " + id
                        ));

        return convertToResponse(teacher);
    }

    // UPDATE
    public TeacherResponseDTO updateTeacher(
            Long id,
            TeacherRequestDTO request) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher not found with id: " + id
                        ));

        validateDuplicates(request, id);

        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        teacher.setQualification(request.getQualification());
        teacher.setSpecialization(request.getSpecialization());
        teacher.setJoiningDate(request.getJoiningDate());

        Teacher updatedTeacher =
                teacherRepository.save(teacher);

        return convertToResponse(updatedTeacher);
    }

    // CHANGE STATUS
    public TeacherResponseDTO updateTeacherStatus(
            Long id,
            TeacherStatus status) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher not found with id: " + id
                        ));

        teacher.setStatus(status);

        Teacher updatedTeacher =
                teacherRepository.save(teacher);

        return convertToResponse(updatedTeacher);
    }

    // DELETE
    public void deleteTeacher(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher not found with id: " + id
                        ));

        teacherRepository.delete(teacher);
    }

    // SEARCH + PAGINATION
    public PageResponse<TeacherResponseDTO> searchTeachers(
            String name,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("firstName").ascending()
        );

        Page<Teacher> teacherPage =
                teacherRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                name,
                                name,
                                pageable
                        );

        List<TeacherResponseDTO> teachers =
                teacherPage.getContent()
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return new PageResponse<>(
                teachers,
                teacherPage.getNumber(),
                teacherPage.getSize(),
                teacherPage.getTotalElements(),
                teacherPage.getTotalPages(),
                teacherPage.isFirst(),
                teacherPage.isLast()
        );
    }

    // FIND BY EMPLOYEE ID
    public TeacherResponseDTO getTeacherByEmployeeId(
            String employeeId) {

        Teacher teacher =
                teacherRepository.findByEmployeeId(employeeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher not found with employee ID: "
                                                + employeeId
                                ));

        return convertToResponse(teacher);
    }

    // FIND BY EMAIL
    public TeacherResponseDTO getTeacherByEmail(
            String email) {

        Teacher teacher =
                teacherRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher not found with email: "
                                                + email
                                ));

        return convertToResponse(teacher);
    }

    // FIND BY PHONE
    public TeacherResponseDTO getTeacherByPhone(
            String phone) {

        Teacher teacher =
                teacherRepository.findByPhone(phone)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher not found with phone: "
                                                + phone
                                ));

        return convertToResponse(teacher);
    }

    // DUPLICATE VALIDATION
    private void validateDuplicates(
            TeacherRequestDTO request,
            Long teacherId) {

        if (teacherId == null) {

            if (teacherRepository.existsByEmployeeId(
                    request.getEmployeeId())) {

                throw new DuplicateResourceException(
                        "Teacher with employee ID "
                                + request.getEmployeeId()
                                + " already exists"
                );
            }

            if (teacherRepository.existsByEmail(
                    request.getEmail())) {

                throw new DuplicateResourceException(
                        "Teacher with email "
                                + request.getEmail()
                                + " already exists"
                );
            }

            if (teacherRepository.existsByPhone(
                    request.getPhone())) {

                throw new DuplicateResourceException(
                        "Teacher with phone "
                                + request.getPhone()
                                + " already exists"
                );
            }

        } else {

            if (teacherRepository
                    .existsByEmployeeIdAndIdNot(
                            request.getEmployeeId(),
                            teacherId)) {

                throw new DuplicateResourceException(
                        "Another teacher already uses employee ID "
                                + request.getEmployeeId()
                );
            }

            if (teacherRepository
                    .existsByEmailAndIdNot(
                            request.getEmail(),
                            teacherId)) {

                throw new DuplicateResourceException(
                        "Another teacher already uses email "
                                + request.getEmail()
                );
            }

            if (teacherRepository
                    .existsByPhoneAndIdNot(
                            request.getPhone(),
                            teacherId)) {

                throw new DuplicateResourceException(
                        "Another teacher already uses phone "
                                + request.getPhone()
                );
            }
        }
    }

    // ENTITY → RESPONSE DTO
    private TeacherResponseDTO convertToResponse(
            Teacher teacher) {

        return new TeacherResponseDTO(
                teacher.getId(),
                teacher.getEmployeeId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getPhone(),
                teacher.getQualification(),
                teacher.getSpecialization(),
                teacher.getJoiningDate(),
                teacher.getStatus()
        );
    }
}
