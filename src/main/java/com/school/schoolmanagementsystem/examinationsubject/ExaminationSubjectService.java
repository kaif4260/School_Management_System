package com.school.schoolmanagementsystem.examinationsubject;

import com.school.schoolmanagementsystem.classmanagement.ClassRepository;
import com.school.schoolmanagementsystem.classmanagement.SchoolClass;
import com.school.schoolmanagementsystem.examination.Examination;
import com.school.schoolmanagementsystem.examination.ExaminationRepository;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.school.schoolmanagementsystem.examinationsubject.dto.ExaminationSubjectRequestDTO;
import com.school.schoolmanagementsystem.examinationsubject.dto.ExaminationSubjectResponseDTO;
import com.school.schoolmanagementsystem.subject.Subject;
import com.school.schoolmanagementsystem.subject.SubjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExaminationSubjectService {

    private final ExaminationSubjectRepository
            examinationSubjectRepository;

    private final ExaminationRepository
            examinationRepository;

    private final SubjectRepository
            subjectRepository;

    private final ClassRepository
            classRepository;

    public ExaminationSubjectService(
            ExaminationSubjectRepository
                    examinationSubjectRepository,
            ExaminationRepository examinationRepository,
            SubjectRepository subjectRepository,
            ClassRepository classRepository) {

        this.examinationSubjectRepository =
                examinationSubjectRepository;

        this.examinationRepository =
                examinationRepository;

        this.subjectRepository =
                subjectRepository;

        this.classRepository =
                classRepository;
    }

    // CREATE
    public ExaminationSubjectResponseDTO create(
            ExaminationSubjectRequestDTO request) {

        if (request.getPassMarks()
                > request.getMaxMarks()) {

            throw new IllegalArgumentException(
                    "Pass marks cannot be greater than maximum marks"
            );
        }

        Examination examination =
                examinationRepository.findById(
                        request.getExaminationId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Examination not found with id: "
                                        + request.getExaminationId()
                        )
                );

        Subject subject =
                subjectRepository.findById(
                        request.getSubjectId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subject not found with id: "
                                        + request.getSubjectId()
                        )
                );

        SchoolClass schoolClass =
                classRepository.findById(
                        request.getClassId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Class not found with id: "
                                        + request.getClassId()
                        )
                );

        if (examinationSubjectRepository
                .existsByExaminationIdAndSubjectIdAndSchoolClassId(
                        request.getExaminationId(),
                        request.getSubjectId(),
                        request.getClassId()
                )) {

            throw new DuplicateResourceException(
                    "This subject is already added to this "
                            + "examination for this class"
            );
        }

        // Exam date must be inside examination period
        LocalDate examDate =
                request.getExamDate();

        if (examDate.isBefore(
                examination.getStartDate()
        ) ||
                examDate.isAfter(
                        examination.getEndDate()
                )) {

            throw new IllegalArgumentException(
                    "Exam date must be between "
                            + examination.getStartDate()
                            + " and "
                            + examination.getEndDate()
            );
        }

        ExaminationSubject examinationSubject =
                new ExaminationSubject();

        examinationSubject.setExamination(
                examination
        );

        examinationSubject.setSubject(
                subject
        );

        examinationSubject.setSchoolClass(
                schoolClass
        );

        examinationSubject.setMaxMarks(
                request.getMaxMarks()
        );

        examinationSubject.setPassMarks(
                request.getPassMarks()
        );

        examinationSubject.setExamDate(
                request.getExamDate()
        );

        ExaminationSubject saved =
                examinationSubjectRepository.save(
                        examinationSubject
                );

        return convertToResponse(saved);
    }

    // GET ALL
    public List<ExaminationSubjectResponseDTO>
    getAll() {

        return examinationSubjectRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public ExaminationSubjectResponseDTO
    getById(Long id) {

        ExaminationSubject examinationSubject =
                examinationSubjectRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Examination subject not found "
                                                + "with id: "
                                                + id
                                )
                        );

        return convertToResponse(
                examinationSubject
        );
    }

    // GET SUBJECTS FOR EXAM
    public List<ExaminationSubjectResponseDTO>
    getByExamination(Long examinationId) {

        if (!examinationRepository
                .existsById(examinationId)) {

            throw new ResourceNotFoundException(
                    "Examination not found with id: "
                            + examinationId
            );
        }

        return examinationSubjectRepository
                .findByExaminationId(examinationId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET EXAMS FOR SUBJECT
    public List<ExaminationSubjectResponseDTO>
    getBySubject(Long subjectId) {

        if (!subjectRepository
                .existsById(subjectId)) {

            throw new ResourceNotFoundException(
                    "Subject not found with id: "
                            + subjectId
            );
        }

        return examinationSubjectRepository
                .findBySubjectId(subjectId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET EXAM SUBJECTS FOR CLASS
    public List<ExaminationSubjectResponseDTO>
    getByClass(Long classId) {

        if (!classRepository
                .existsById(classId)) {

            throw new ResourceNotFoundException(
                    "Class not found with id: "
                            + classId
            );
        }

        return examinationSubjectRepository
                .findBySchoolClassId(classId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET SUBJECTS FOR EXAM + CLASS
    public List<ExaminationSubjectResponseDTO>
    getByExaminationAndClass(
            Long examinationId,
            Long classId) {

        if (!examinationRepository
                .existsById(examinationId)) {

            throw new ResourceNotFoundException(
                    "Examination not found with id: "
                            + examinationId
            );
        }

        if (!classRepository
                .existsById(classId)) {

            throw new ResourceNotFoundException(
                    "Class not found with id: "
                            + classId
            );
        }

        return examinationSubjectRepository
                .findByExaminationIdAndSchoolClassId(
                        examinationId,
                        classId
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // DELETE
    public void delete(Long id) {

        ExaminationSubject examinationSubject =
                examinationSubjectRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Examination subject not found "
                                                + "with id: "
                                                + id
                                )
                        );

        examinationSubjectRepository.delete(
                examinationSubject
        );
    }

    // ENTITY → DTO
    private ExaminationSubjectResponseDTO convertToResponse(
            ExaminationSubject examinationSubject) {

        return new ExaminationSubjectResponseDTO(

                // Examination
                examinationSubject
                        .getId(),

                examinationSubject
                        .getExamination()
                        .getId(),

                examinationSubject
                        .getExamination()
                        .getName(),

                // Subject
                examinationSubject
                        .getSubject()
                        .getId(),

                examinationSubject
                        .getSubject()
                        .getName(),

                // Class
                examinationSubject
                        .getSchoolClass()
                        .getId(),

                examinationSubject
                        .getSchoolClass()
                        .getName(),

                // Marks
                examinationSubject
                        .getMaxMarks(),

                examinationSubject
                        .getPassMarks(),

                // Exam date
                examinationSubject
                        .getExamDate()
        );
    }
}
