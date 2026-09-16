package com.school.schoolmanagementsystem.subject;

import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.school.schoolmanagementsystem.subject.dto.SubjectRequestDTO;
import com.school.schoolmanagementsystem.subject.dto.SubjectResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(
            SubjectRepository subjectRepository) {

        this.subjectRepository = subjectRepository;
    }

    // CREATE
    public SubjectResponseDTO createSubject(
            SubjectRequestDTO request) {

        if (subjectRepository.existsByCodeIgnoreCase(
                request.getCode())) {

            throw new DuplicateResourceException(
                    "Subject with code "
                            + request.getCode()
                            + " already exists"
            );
        }

        if (subjectRepository.existsByNameIgnoreCase(
                request.getName())) {

            throw new DuplicateResourceException(
                    "Subject with name "
                            + request.getName()
                            + " already exists"
            );
        }

        Subject subject = new Subject();

        subject.setCode(request.getCode());
        subject.setName(request.getName());
        subject.setDescription(request.getDescription());
        subject.setStatus(SubjectStatus.ACTIVE);

        Subject savedSubject =
                subjectRepository.save(subject);

        return convertToResponse(savedSubject);
    }

    // GET ALL
    public List<SubjectResponseDTO> getAllSubjects() {

        return subjectRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public SubjectResponseDTO getSubjectById(Long id) {

        Subject subject =
                subjectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not found with id: "
                                                + id
                                ));

        return convertToResponse(subject);
    }

    // GET BY CODE
    public SubjectResponseDTO getSubjectByCode(
            String code) {

        Subject subject =
                subjectRepository
                        .findByCodeIgnoreCase(code)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not found with code: "
                                                + code
                                ));

        return convertToResponse(subject);
    }

    // GET BY NAME
    public SubjectResponseDTO getSubjectByName(
            String name) {

        Subject subject =
                subjectRepository
                        .findByNameIgnoreCase(name)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not found with name: "
                                                + name
                                ));

        return convertToResponse(subject);
    }

    // UPDATE
    public SubjectResponseDTO updateSubject(
            Long id,
            SubjectRequestDTO request) {

        Subject subject =
                subjectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not found with id: "
                                                + id
                                ));

        if (subjectRepository
                .existsByCodeIgnoreCaseAndIdNot(
                        request.getCode(),
                        id)) {

            throw new DuplicateResourceException(
                    "Another subject already uses code "
                            + request.getCode()
            );
        }

        if (subjectRepository
                .existsByNameIgnoreCaseAndIdNot(
                        request.getName(),
                        id)) {

            throw new DuplicateResourceException(
                    "Another subject already uses name "
                            + request.getName()
            );
        }

        subject.setCode(request.getCode());
        subject.setName(request.getName());
        subject.setDescription(request.getDescription());

        Subject updatedSubject =
                subjectRepository.save(subject);

        return convertToResponse(updatedSubject);
    }

    // CHANGE STATUS
    public SubjectResponseDTO updateSubjectStatus(
            Long id,
            SubjectStatus status) {

        Subject subject =
                subjectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not found with id: "
                                                + id
                                ));

        subject.setStatus(status);

        Subject updatedSubject =
                subjectRepository.save(subject);

        return convertToResponse(updatedSubject);
    }

    // DELETE
    public void deleteSubject(Long id) {

        Subject subject =
                subjectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not found with id: "
                                                + id
                                ));

        subjectRepository.delete(subject);
    }

    // SEARCH + PAGINATION
    public PageResponse<SubjectResponseDTO> searchSubjects(
            String name,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("name").ascending()
        );

        Page<Subject> subjectPage =
                subjectRepository
                        .findByNameContainingIgnoreCase(
                                name,
                                pageable
                        );

        List<SubjectResponseDTO> subjects =
                subjectPage.getContent()
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return new PageResponse<>(
                subjects,
                subjectPage.getNumber(),
                subjectPage.getSize(),
                subjectPage.getTotalElements(),
                subjectPage.getTotalPages(),
                subjectPage.isFirst(),
                subjectPage.isLast()
        );
    }

    // ENTITY → DTO
    private SubjectResponseDTO convertToResponse(
            Subject subject) {

        return new SubjectResponseDTO(
                subject.getId(),
                subject.getCode(),
                subject.getName(),
                subject.getDescription(),
                subject.getStatus()
        );
    }
}
