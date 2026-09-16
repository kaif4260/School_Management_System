package com.school.schoolmanagementsystem.studentmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentMarksRepository
        extends JpaRepository<StudentMarks, Long> {

    boolean existsByEnrollmentIdAndExaminationSubjectId(
            Long enrollmentId,
            Long examinationSubjectId
    );

    Optional<StudentMarks>
    findByEnrollmentIdAndExaminationSubjectId(
            Long enrollmentId,
            Long examinationSubjectId
    );

    List<StudentMarks>
    findByEnrollmentId(Long enrollmentId);

    List<StudentMarks>
    findByExaminationSubjectId(
            Long examinationSubjectId
    );

    List<StudentMarks>
    findByExaminationSubject_Examination_Id(
            Long examinationId
    );
}