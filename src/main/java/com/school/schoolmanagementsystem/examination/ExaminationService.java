package com.school.schoolmanagementsystem.examination;

import com.school.schoolmanagementsystem.examination.dto.ExaminationRequestDTO;
import com.school.schoolmanagementsystem.examination.dto.ExaminationResponseDTO;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    public ExaminationService(
            ExaminationRepository examinationRepository) {

        this.examinationRepository = examinationRepository;
    }

    // CREATE
    public ExaminationResponseDTO createExamination(
            ExaminationRequestDTO request) {

        if (examinationRepository
                .existsByNameIgnoreCaseAndAcademicYear(
                        request.getName(),
                        request.getAcademicYear())) {

            throw new DuplicateResourceException(
                    "Examination already exists for academic year "
                            + request.getAcademicYear()
            );
        }

        Examination examination =
                new Examination();

        examination.setName(request.getName());
        examination.setDescription(request.getDescription());
        examination.setAcademicYear(
                request.getAcademicYear()
        );
        examination.setStartDate(
                request.getStartDate()
        );
        examination.setEndDate(
                request.getEndDate()
        );

        examination.setStatus(
                ExaminationStatus.UPCOMING
        );

        Examination saved =
                examinationRepository.save(examination);

        return convertToResponse(saved);
    }

    // GET ALL
    public List<ExaminationResponseDTO>
    getAllExaminations() {

        return examinationRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public ExaminationResponseDTO
    getExaminationById(Long id) {

        Examination examination =
                examinationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Examination not found with id: "
                                                + id
                                ));

        return convertToResponse(examination);
    }

    // GET BY ACADEMIC YEAR
    public List<ExaminationResponseDTO>
    getExaminationsByAcademicYear(
            String academicYear) {

        return examinationRepository
                .findByAcademicYear(academicYear)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY STATUS
    public List<ExaminationResponseDTO>
    getExaminationsByStatus(
            ExaminationStatus status) {

        return examinationRepository
                .findByStatus(status)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE
    public ExaminationResponseDTO updateExamination(
            Long id,
            ExaminationRequestDTO request) {

        Examination examination =
                examinationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Examination not found with id: "
                                                + id
                                ));

        if (examinationRepository
                .existsByNameIgnoreCaseAndAcademicYear(
                        request.getName(),
                        request.getAcademicYear())
                &&
                !(
                        examination.getName()
                                .equalsIgnoreCase(
                                        request.getName()
                                )
                                &&
                                examination.getAcademicYear()
                                        .equals(
                                                request.getAcademicYear()
                                        )
                )) {

            throw new DuplicateResourceException(
                    "Examination already exists for academic year "
                            + request.getAcademicYear()
            );
        }

        examination.setName(request.getName());
        examination.setDescription(request.getDescription());
        examination.setAcademicYear(
                request.getAcademicYear()
        );
        examination.setStartDate(
                request.getStartDate()
        );
        examination.setEndDate(
                request.getEndDate()
        );

        Examination updated =
                examinationRepository.save(examination);

        return convertToResponse(updated);
    }

    // UPDATE STATUS
    public ExaminationResponseDTO updateStatus(
            Long id,
            ExaminationStatus status) {

        Examination examination =
                examinationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Examination not found with id: "
                                                + id
                                ));

        examination.setStatus(status);

        Examination updated =
                examinationRepository.save(examination);

        return convertToResponse(updated);
    }

    // DELETE
    public void deleteExamination(Long id) {

        Examination examination =
                examinationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Examination not found with id: "
                                                + id
                                ));

        examinationRepository.delete(examination);
    }

    // ENTITY → DTO
    private ExaminationResponseDTO convertToResponse(
            Examination examination) {

        return new ExaminationResponseDTO(
                examination.getId(),
                examination.getName(),
                examination.getDescription(),
                examination.getAcademicYear(),
                examination.getStartDate(),
                examination.getEndDate(),
                examination.getStatus()
        );
    }
}
