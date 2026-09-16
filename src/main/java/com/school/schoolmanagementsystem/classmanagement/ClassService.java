package com.school.schoolmanagementsystem.classmanagement;

import com.school.schoolmanagementsystem.classmanagement.dto.ClassRequestDTO;
import com.school.schoolmanagementsystem.classmanagement.dto.ClassResponseDTO;
import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    // CREATE
    public ClassResponseDTO createClass(
            ClassRequestDTO request) {

        if (classRepository.existsByNameIgnoreCase(
                request.getName())) {

            throw new DuplicateResourceException(
                    "Class with name "
                            + request.getName()
                            + " already exists"
            );
        }

        SchoolClass schoolClass = new SchoolClass();

        schoolClass.setName(request.getName());
        schoolClass.setDescription(request.getDescription());
        schoolClass.setStatus(ClassStatus.ACTIVE);

        SchoolClass savedClass =
                classRepository.save(schoolClass);

        return convertToResponse(savedClass);
    }

    // GET ALL
    public List<ClassResponseDTO> getAllClasses() {

        return classRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public ClassResponseDTO getClassById(Long id) {

        SchoolClass schoolClass =
                classRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Class not found with id: " + id
                                ));

        return convertToResponse(schoolClass);
    }

    // UPDATE
    public ClassResponseDTO updateClass(
            Long id,
            ClassRequestDTO request) {

        SchoolClass schoolClass =
                classRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Class not found with id: " + id
                                ));

        if (classRepository
                .existsByNameIgnoreCaseAndIdNot(
                        request.getName(),
                        id)) {

            throw new DuplicateResourceException(
                    "Another class already uses the name "
                            + request.getName()
            );
        }

        schoolClass.setName(request.getName());
        schoolClass.setDescription(request.getDescription());

        SchoolClass updatedClass =
                classRepository.save(schoolClass);

        return convertToResponse(updatedClass);
    }

    // CHANGE STATUS
    public ClassResponseDTO updateClassStatus(
            Long id,
            ClassStatus status) {

        SchoolClass schoolClass =
                classRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Class not found with id: " + id
                                ));

        schoolClass.setStatus(status);

        SchoolClass updatedClass =
                classRepository.save(schoolClass);

        return convertToResponse(updatedClass);
    }

    // DELETE
    public void deleteClass(Long id) {

        SchoolClass schoolClass =
                classRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Class not found with id: " + id
                                ));

        classRepository.delete(schoolClass);
    }

    // SEARCH + PAGINATION
    public PageResponse<ClassResponseDTO> searchClasses(
            String name,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("name").ascending()
        );

        Page<SchoolClass> classPage =
                classRepository
                        .findByNameContainingIgnoreCase(
                                name,
                                pageable
                        );

        List<ClassResponseDTO> classes =
                classPage.getContent()
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return new PageResponse<>(
                classes,
                classPage.getNumber(),
                classPage.getSize(),
                classPage.getTotalElements(),
                classPage.getTotalPages(),
                classPage.isFirst(),
                classPage.isLast()
        );
    }

    // ENTITY → DTO
    private ClassResponseDTO convertToResponse(
            SchoolClass schoolClass) {

        return new ClassResponseDTO(
                schoolClass.getId(),
                schoolClass.getName(),
                schoolClass.getDescription(),
                schoolClass.getStatus()
        );
    }
}
