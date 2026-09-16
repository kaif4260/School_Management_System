package com.school.schoolmanagementsystem.section;

import com.school.schoolmanagementsystem.classmanagement.ClassRepository;
import com.school.schoolmanagementsystem.classmanagement.SchoolClass;
import com.school.schoolmanagementsystem.dto.PageResponse;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.school.schoolmanagementsystem.section.dto.SectionRequestDTO;
import com.school.schoolmanagementsystem.section.dto.SectionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ClassRepository classRepository;

    public SectionService(
            SectionRepository sectionRepository,
            ClassRepository classRepository) {

        this.sectionRepository = sectionRepository;
        this.classRepository = classRepository;
    }

    // CREATE
    public SectionResponseDTO createSection(
            SectionRequestDTO request) {

        SchoolClass schoolClass =
                classRepository.findById(request.getClassId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Class not found with id: "
                                                + request.getClassId()
                                ));

        if (sectionRepository
                .existsByNameIgnoreCaseAndSchoolClassId(
                        request.getName(),
                        request.getClassId())) {

            throw new DuplicateResourceException(
                    "Section "
                            + request.getName()
                            + " already exists in "
                            + schoolClass.getName()
            );
        }

        Section section = new Section();

        section.setName(request.getName());
        section.setDescription(request.getDescription());
        section.setStatus(SectionStatus.ACTIVE);
        section.setSchoolClass(schoolClass);

        Section savedSection =
                sectionRepository.save(section);

        return convertToResponse(savedSection);
    }

    // GET ALL
    public List<SectionResponseDTO> getAllSections() {

        return sectionRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public SectionResponseDTO getSectionById(Long id) {

        Section section =
                sectionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section not found with id: "
                                                + id
                                ));

        return convertToResponse(section);
    }

    // GET SECTIONS BY CLASS
    public List<SectionResponseDTO> getSectionsByClass(
            Long classId) {

        if (!classRepository.existsById(classId)) {

            throw new ResourceNotFoundException(
                    "Class not found with id: " + classId
            );
        }

        return sectionRepository
                .findBySchoolClassId(classId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE
    public SectionResponseDTO updateSection(
            Long id,
            SectionRequestDTO request) {

        Section section =
                sectionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section not found with id: "
                                                + id
                                ));

        SchoolClass schoolClass =
                classRepository.findById(request.getClassId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Class not found with id: "
                                                + request.getClassId()
                                ));

        if (sectionRepository
                .existsByNameIgnoreCaseAndSchoolClassIdAndIdNot(
                        request.getName(),
                        request.getClassId(),
                        id)) {

            throw new DuplicateResourceException(
                    "Section "
                            + request.getName()
                            + " already exists in "
                            + schoolClass.getName()
            );
        }

        section.setName(request.getName());
        section.setDescription(request.getDescription());
        section.setSchoolClass(schoolClass);

        Section updatedSection =
                sectionRepository.save(section);

        return convertToResponse(updatedSection);
    }

    // CHANGE STATUS
    public SectionResponseDTO updateSectionStatus(
            Long id,
            SectionStatus status) {

        Section section =
                sectionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section not found with id: "
                                                + id
                                ));

        section.setStatus(status);

        Section updatedSection =
                sectionRepository.save(section);

        return convertToResponse(updatedSection);
    }

    // DELETE
    public void deleteSection(Long id) {

        Section section =
                sectionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section not found with id: "
                                                + id
                                ));

        sectionRepository.delete(section);
    }

    // SEARCH + PAGINATION
    public PageResponse<SectionResponseDTO> searchSections(
            String name,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("name").ascending()
        );

        Page<Section> sectionPage =
                sectionRepository
                        .findByNameContainingIgnoreCase(
                                name,
                                pageable
                        );

        List<SectionResponseDTO> sections =
                sectionPage.getContent()
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return new PageResponse<>(
                sections,
                sectionPage.getNumber(),
                sectionPage.getSize(),
                sectionPage.getTotalElements(),
                sectionPage.getTotalPages(),
                sectionPage.isFirst(),
                sectionPage.isLast()
        );
    }

    // ENTITY → DTO
    private SectionResponseDTO convertToResponse(
            Section section) {

        return new SectionResponseDTO(
                section.getId(),
                section.getName(),
                section.getDescription(),
                section.getStatus(),
                section.getSchoolClass().getId(),
                section.getSchoolClass().getName()
        );
    }
}
