package com.school.schoolmanagementsystem.studentmarks;

import com.school.schoolmanagementsystem.enrollment.StudentEnrollment;
import com.school.schoolmanagementsystem.enrollment.StudentEnrollmentRepository;
import com.school.schoolmanagementsystem.examinationsubject.ExaminationSubject;
import com.school.schoolmanagementsystem.examinationsubject.ExaminationSubjectRepository;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.school.schoolmanagementsystem.studentmarks.dto.StudentMarksRequestDTO;
import com.school.schoolmanagementsystem.studentmarks.dto.StudentMarksResponseDTO;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentMarksService {

    private final StudentMarksRepository studentMarksRepository;

    private final StudentEnrollmentRepository
            studentEnrollmentRepository;

    private final ExaminationSubjectRepository
            examinationSubjectRepository;


    public StudentMarksService(
            StudentMarksRepository studentMarksRepository,
            StudentEnrollmentRepository studentEnrollmentRepository,
            ExaminationSubjectRepository examinationSubjectRepository) {

        this.studentMarksRepository =
                studentMarksRepository;

        this.studentEnrollmentRepository =
                studentEnrollmentRepository;

        this.examinationSubjectRepository =
                examinationSubjectRepository;
    }

    // CREATE MARKS

    public StudentMarksResponseDTO create(
            StudentMarksRequestDTO request) {

        StudentEnrollment enrollment =
                studentEnrollmentRepository.findById(
                        request.getEnrollmentId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment not found with id: "
                                        + request.getEnrollmentId()
                        )
                );


        ExaminationSubject examinationSubject =
                examinationSubjectRepository.findById(
                        request.getExaminationSubjectId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Examination subject not found with id: "
                                        + request.getExaminationSubjectId()
                        )
                );


        // Prevent duplicate marks
        if (studentMarksRepository
                .existsByEnrollmentIdAndExaminationSubjectId(
                        request.getEnrollmentId(),
                        request.getExaminationSubjectId()
                )) {

            throw new DuplicateResourceException(
                    "Marks already exist for this enrollment "
                            + "and examination subject"
            );
        }

        // CHECK CLASS

        Long enrollmentClassId =
                enrollment
                        .getSchoolClass()
                        .getId();

        Long examinationClassId =
                examinationSubject
                        .getSchoolClass()
                        .getId();


        if (!enrollmentClassId.equals(examinationClassId)) {

            throw new IllegalArgumentException(
                    "Student is not enrolled in the class "
                            + "for this examination subject"
            );
        }

        // CHECK MAXIMUM MARKS

        if (request.getObtainedMarks()
                > examinationSubject.getMaxMarks()) {

            throw new IllegalArgumentException(
                    "Obtained marks cannot be greater than "
                            + "maximum marks: "
                            + examinationSubject.getMaxMarks()
            );
        }

        // CREATE MARKS

        StudentMarks studentMarks =
                new StudentMarks();

        studentMarks.setEnrollment(enrollment);

        studentMarks.setExaminationSubject(
                examinationSubject
        );

        studentMarks.setObtainedMarks(
                request.getObtainedMarks()
        );


        StudentMarks saved =
                studentMarksRepository.save(
                        studentMarks
                );


        return convertToResponse(saved);
    }

    // GET ALL

    public List<StudentMarksResponseDTO> getAll() {

        return studentMarksRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID

    public StudentMarksResponseDTO getById(Long id) {

        StudentMarks marks =
                studentMarksRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Marks not found with id: "
                                                + id
                                )
                        );

        return convertToResponse(marks);
    }

    // GET MARKS BY STUDENT

    public List<StudentMarksResponseDTO>
    getByStudent(Long studentId) {

        List<StudentEnrollment> enrollments =
                studentEnrollmentRepository
                        .findByStudentId(studentId);

        if (enrollments.isEmpty()) {

            throw new ResourceNotFoundException(
                    "No enrollment found for student id: "
                            + studentId
            );
        }

        return enrollments
                .stream()
                .flatMap(enrollment ->
                        studentMarksRepository
                                .findByEnrollmentId(
                                        enrollment.getId()
                                )
                                .stream()
                )
                .map(this::convertToResponse)
                .toList();
    }

    // GET MARKS BY ENROLLMENT

    public List<StudentMarksResponseDTO>
    getByEnrollment(Long enrollmentId) {

        if (!studentEnrollmentRepository
                .existsById(enrollmentId)) {

            throw new ResourceNotFoundException(
                    "Enrollment not found with id: "
                            + enrollmentId
            );
        }

        return studentMarksRepository
                .findByEnrollmentId(enrollmentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET MARKS BY EXAMINATION SUBJECT

    public List<StudentMarksResponseDTO>
    getByExaminationSubject(
            Long examinationSubjectId) {

        if (!examinationSubjectRepository
                .existsById(examinationSubjectId)) {

            throw new ResourceNotFoundException(
                    "Examination subject not found with id: "
                            + examinationSubjectId
            );
        }

        return studentMarksRepository
                .findByExaminationSubjectId(
                        examinationSubjectId
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET MARKS BY EXAMINATION

    public List<StudentMarksResponseDTO>
    getByExamination(Long examinationId) {

        return studentMarksRepository
                .findByExaminationSubject_Examination_Id(
                        examinationId
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE MARKS

    public StudentMarksResponseDTO update(
            Long id,
            StudentMarksRequestDTO request) {

        StudentMarks marks =
                studentMarksRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Marks not found with id: "
                                                + id
                                )
                        );


        StudentEnrollment enrollment =
                studentEnrollmentRepository.findById(
                        request.getEnrollmentId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment not found with id: "
                                        + request.getEnrollmentId()
                        )
                );


        ExaminationSubject examinationSubject =
                examinationSubjectRepository.findById(
                        request.getExaminationSubjectId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Examination subject not found with id: "
                                        + request.getExaminationSubjectId()
                        )
                );


        // Check class
        if (!enrollment
                .getSchoolClass()
                .getId()
                .equals(
                        examinationSubject
                                .getSchoolClass()
                                .getId()
                )) {

            throw new IllegalArgumentException(
                    "Student is not enrolled in the class "
                            + "for this examination subject"
            );
        }


        // Check maximum marks
        if (request.getObtainedMarks()
                > examinationSubject.getMaxMarks()) {

            throw new IllegalArgumentException(
                    "Obtained marks cannot be greater than "
                            + "maximum marks: "
                            + examinationSubject.getMaxMarks()
            );
        }


        marks.setEnrollment(enrollment);

        marks.setExaminationSubject(
                examinationSubject
        );

        marks.setObtainedMarks(
                request.getObtainedMarks()
        );


        StudentMarks updated =
                studentMarksRepository.save(
                        marks
                );


        return convertToResponse(updated);
    }

    // DELETE

    public void delete(Long id) {

        StudentMarks marks =
                studentMarksRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Marks not found with id: "
                                                + id
                                )
                        );

        studentMarksRepository.delete(marks);
    }

    // CONVERT ENTITY → RESPONSE

    private StudentMarksResponseDTO
    convertToResponse(StudentMarks marks) {

        StudentEnrollment enrollment =
                marks.getEnrollment();

        ExaminationSubject examSubject =
                marks.getExaminationSubject();


        double percentage =
                ((double) marks.getObtainedMarks()
                        / examSubject.getMaxMarks())
                        * 100;


        String grade =
                calculateGrade(percentage);


        String studentName =
                enrollment
                        .getStudent()
                        .getFirstName()
                        + " "
                        + enrollment
                        .getStudent()
                        .getLastName();


        return new StudentMarksResponseDTO(

                marks.getId(),

                enrollment.getId(),

                enrollment
                        .getStudent()
                        .getId(),

                studentName,

                examSubject
                        .getExamination()
                        .getId(),

                examSubject
                        .getExamination()
                        .getName(),

                examSubject.getId(),

                examSubject
                        .getSubject()
                        .getId(),

                examSubject
                        .getSubject()
                        .getName(),

                enrollment
                        .getSchoolClass()
                        .getId(),

                enrollment
                        .getSchoolClass()
                        .getName(),

                enrollment
                        .getSection()
                        .getId(),

                enrollment
                        .getSection()
                        .getName(),

                enrollment.getAcademicYear(),

                examSubject.getMaxMarks(),

                examSubject.getPassMarks(),

                marks.getObtainedMarks(),

                Math.round(percentage * 100.0) / 100.0,

                grade,

                examSubject.getExamDate()
        );
    }

    // GRADE

    private String calculateGrade(
            double percentage) {

        if (percentage >= 90) {
            return "A+";
        }

        if (percentage >= 80) {
            return "A";
        }

        if (percentage >= 70) {
            return "B+";
        }

        if (percentage >= 60) {
            return "B";
        }

        if (percentage >= 50) {
            return "C";
        }

        if (percentage >= 40) {
            return "D";
        }

        return "F";
    }
}