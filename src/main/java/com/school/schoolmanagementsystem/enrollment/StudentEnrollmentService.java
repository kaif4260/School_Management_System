package com.school.schoolmanagementsystem.enrollment;

import com.school.schoolmanagementsystem.classmanagement.ClassRepository;
import com.school.schoolmanagementsystem.classmanagement.SchoolClass;
import com.school.schoolmanagementsystem.enrollment.dto.EnrollmentRequestDTO;
import com.school.schoolmanagementsystem.enrollment.dto.EnrollmentResponseDTO;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.school.schoolmanagementsystem.section.Section;
import com.school.schoolmanagementsystem.section.SectionRepository;
import com.school.schoolmanagementsystem.student.Student;
import com.school.schoolmanagementsystem.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentEnrollmentService {

    private final StudentEnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final SectionRepository sectionRepository;

    public StudentEnrollmentService(
            StudentEnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            ClassRepository classRepository,
            SectionRepository sectionRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.sectionRepository = sectionRepository;
    }

    // CREATE ENROLLMENT
    public EnrollmentResponseDTO enrollStudent(
            EnrollmentRequestDTO request) {

        Student student =
                studentRepository.findById(request.getStudentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student not found with id: "
                                                + request.getStudentId()
                                ));

        SchoolClass schoolClass =
                classRepository.findById(request.getClassId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Class not found with id: "
                                                + request.getClassId()
                                ));

        Section section =
                sectionRepository.findById(request.getSectionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section not found with id: "
                                                + request.getSectionId()
                                ));

        // Check section belongs to class
        if (!section.getSchoolClass()
                .getId()
                .equals(schoolClass.getId())) {

            throw new IllegalArgumentException(
                    "Section does not belong to the selected class"
            );
        }

        // Student can only have one enrollment in the same academic year

        if (enrollmentRepository
                .existsByStudentIdAndAcademicYear(
                        request.getStudentId(),
                        request.getAcademicYear())) {

            throw new DuplicateResourceException(
                    "Student is already enrolled for academic year "
                            + request.getAcademicYear()
            );
        }

        StudentEnrollment enrollment =
                new StudentEnrollment();

        enrollment.setStudent(student);
        enrollment.setSchoolClass(schoolClass);
        enrollment.setSection(section);
        enrollment.setAcademicYear(
                request.getAcademicYear()
        );
        enrollment.setStatus(
                EnrollmentStatus.ACTIVE
        );

        StudentEnrollment saved =
                enrollmentRepository.save(enrollment);

        return convertToResponse(saved);
    }

    // GET ALL
    public List<EnrollmentResponseDTO>
    getAllEnrollments() {

        return enrollmentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public EnrollmentResponseDTO
    getEnrollmentById(Long id) {

        StudentEnrollment enrollment =
                enrollmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Enrollment not found with id: "
                                                + id
                                ));

        return convertToResponse(enrollment);
    }

    // GET STUDENT HISTORY
    public List<EnrollmentResponseDTO>
    getStudentEnrollments(Long studentId) {

        if (!studentRepository.existsById(studentId)) {

            throw new ResourceNotFoundException(
                    "Student not found with id: "
                            + studentId
            );
        }

        return enrollmentRepository
                .findByStudentId(studentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY CLASS
    public List<EnrollmentResponseDTO>
    getClassEnrollments(Long classId) {

        if (!classRepository.existsById(classId)) {

            throw new ResourceNotFoundException(
                    "Class not found with id: "
                            + classId
            );
        }

        return enrollmentRepository
                .findBySchoolClassId(classId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY SECTION
    public List<EnrollmentResponseDTO>
    getSectionEnrollments(Long sectionId) {

        if (!sectionRepository.existsById(sectionId)) {

            throw new ResourceNotFoundException(
                    "Section not found with id: "
                            + sectionId
            );
        }

        return enrollmentRepository
                .findBySectionId(sectionId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ACADEMIC YEAR
    public List<EnrollmentResponseDTO>
    getEnrollmentsByAcademicYear(
            String academicYear) {

        return enrollmentRepository
                .findByAcademicYear(academicYear)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE STATUS
    public EnrollmentResponseDTO
    updateEnrollmentStatus(
            Long id,
            EnrollmentStatus status) {

        StudentEnrollment enrollment =
                enrollmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Enrollment not found with id: "
                                                + id
                                ));

        enrollment.setStatus(status);

        StudentEnrollment updated =
                enrollmentRepository.save(enrollment);

        return convertToResponse(updated);
    }

    // DELETE
    public void deleteEnrollment(Long id) {

        StudentEnrollment enrollment =
                enrollmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Enrollment not found with id: "
                                                + id
                                ));

        enrollmentRepository.delete(enrollment);
    }

    // ENTITY → DTO
    private EnrollmentResponseDTO convertToResponse(
            StudentEnrollment enrollment) {

        return new EnrollmentResponseDTO(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getSchoolClass().getId(),
                enrollment.getSection().getId(),
                enrollment.getAcademicYear(),
                enrollment.getStatus()
        );
    }
}
