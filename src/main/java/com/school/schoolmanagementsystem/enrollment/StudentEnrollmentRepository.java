package com.school.schoolmanagementsystem.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentEnrollmentRepository
        extends JpaRepository<StudentEnrollment, Long> {

    boolean existsByStudentIdAndAcademicYear(
            Long studentId,
            String academicYear
    );

    Optional<StudentEnrollment>
    findByStudentIdAndAcademicYear(
            Long studentId,
            String academicYear
    );

    List<StudentEnrollment>
    findByStudentId(Long studentId);

    List<StudentEnrollment>
    findBySchoolClassId(Long classId);

    List<StudentEnrollment>
    findBySectionId(Long sectionId);

    List<StudentEnrollment>
    findByAcademicYear(String academicYear);

    List<StudentEnrollment>
    findByStatus(EnrollmentStatus status);
}
