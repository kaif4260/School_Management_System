package com.school.schoolmanagementsystem.teachingassignment;

import com.school.schoolmanagementsystem.classmanagement.ClassRepository;
import com.school.schoolmanagementsystem.classmanagement.SchoolClass;
import com.school.schoolmanagementsystem.exception.DuplicateResourceException;
import com.school.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.school.schoolmanagementsystem.section.Section;
import com.school.schoolmanagementsystem.section.SectionRepository;
import com.school.schoolmanagementsystem.subject.Subject;
import com.school.schoolmanagementsystem.subject.SubjectRepository;
import com.school.schoolmanagementsystem.teacher.Teacher;
import com.school.schoolmanagementsystem.teacher.TeacherRepository;
import com.school.schoolmanagementsystem.teachingassignment.dto.TeachingAssignmentRequestDTO;
import com.school.schoolmanagementsystem.teachingassignment.dto.TeachingAssignmentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingAssignmentService {

    private final TeachingAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;
    private final SectionRepository sectionRepository;

    public TeachingAssignmentService(
            TeachingAssignmentRepository assignmentRepository,
            TeacherRepository teacherRepository,
            SubjectRepository subjectRepository,
            ClassRepository classRepository,
            SectionRepository sectionRepository) {

        this.assignmentRepository = assignmentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.sectionRepository = sectionRepository;
    }

    // CREATE
    public TeachingAssignmentResponseDTO createAssignment(
            TeachingAssignmentRequestDTO request) {

        Teacher teacher =
                teacherRepository.findById(request.getTeacherId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher not found with id: "
                                                + request.getTeacherId()
                                ));

        Subject subject =
                subjectRepository.findById(request.getSubjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not found with id: "
                                                + request.getSubjectId()
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

        // Make sure section belongs to selected class
        if (!section.getSchoolClass()
                .getId()
                .equals(schoolClass.getId())) {

            throw new IllegalArgumentException(
                    "Section does not belong to the selected class"
            );
        }

        // Prevent duplicate assignment
        if (assignmentRepository
                .existsByTeacherIdAndSubjectIdAndSchoolClassIdAndSectionId(
                        request.getTeacherId(),
                        request.getSubjectId(),
                        request.getClassId(),
                        request.getSectionId())) {

            throw new DuplicateResourceException(
                    "This teacher is already assigned to this subject, class and section"
            );
        }

        TeachingAssignment assignment =
                new TeachingAssignment();

        assignment.setTeacher(teacher);
        assignment.setSubject(subject);
        assignment.setSchoolClass(schoolClass);
        assignment.setSection(section);
        assignment.setStatus(AssignmentStatus.ACTIVE);

        TeachingAssignment savedAssignment =
                assignmentRepository.save(assignment);

        return convertToResponse(savedAssignment);
    }

    // GET ALL
    public List<TeachingAssignmentResponseDTO>
    getAllAssignments() {

        return assignmentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public TeachingAssignmentResponseDTO
    getAssignmentById(Long id) {

        TeachingAssignment assignment =
                assignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teaching assignment not found with id: "
                                                + id
                                ));

        return convertToResponse(assignment);
    }

    // GET BY TEACHER
    public List<TeachingAssignmentResponseDTO>
    getAssignmentsByTeacher(Long teacherId) {

        if (!teacherRepository.existsById(teacherId)) {

            throw new ResourceNotFoundException(
                    "Teacher not found with id: " + teacherId
            );
        }

        return assignmentRepository
                .findByTeacherId(teacherId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY SUBJECT
    public List<TeachingAssignmentResponseDTO>
    getAssignmentsBySubject(Long subjectId) {

        if (!subjectRepository.existsById(subjectId)) {

            throw new ResourceNotFoundException(
                    "Subject not found with id: " + subjectId
            );
        }

        return assignmentRepository
                .findBySubjectId(subjectId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY CLASS
    public List<TeachingAssignmentResponseDTO>
    getAssignmentsByClass(Long classId) {

        if (!classRepository.existsById(classId)) {

            throw new ResourceNotFoundException(
                    "Class not found with id: " + classId
            );
        }

        return assignmentRepository
                .findBySchoolClassId(classId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY SECTION
    public List<TeachingAssignmentResponseDTO>
    getAssignmentsBySection(Long sectionId) {

        if (!sectionRepository.existsById(sectionId)) {

            throw new ResourceNotFoundException(
                    "Section not found with id: " + sectionId
            );
        }

        return assignmentRepository
                .findBySectionId(sectionId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE STATUS
    public TeachingAssignmentResponseDTO
    updateAssignmentStatus(
            Long id,
            AssignmentStatus status) {

        TeachingAssignment assignment =
                assignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teaching assignment not found with id: "
                                                + id
                                ));

        assignment.setStatus(status);

        TeachingAssignment updated =
                assignmentRepository.save(assignment);

        return convertToResponse(updated);
    }

    // DELETE
    public void deleteAssignment(Long id) {

        TeachingAssignment assignment =
                assignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teaching assignment not found with id: "
                                                + id
                                ));

        assignmentRepository.delete(assignment);
    }

    // ENTITY → DTO
    private TeachingAssignmentResponseDTO
    convertToResponse(
            TeachingAssignment assignment) {

        return new TeachingAssignmentResponseDTO(
                assignment.getId(),
                assignment.getTeacher().getId(),
                assignment.getSubject().getId(),
                assignment.getSchoolClass().getId(),
                assignment.getSection().getId(),
                assignment.getStatus()
        );
    }
}