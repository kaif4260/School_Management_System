package com.school.schoolmanagementsystem.teachingassignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachingAssignmentRepository
        extends JpaRepository<TeachingAssignment, Long> {

    boolean existsByTeacherIdAndSubjectIdAndSchoolClassIdAndSectionId(
            Long teacherId,
            Long subjectId,
            Long classId,
            Long sectionId
    );

    List<TeachingAssignment> findByTeacherId(
            Long teacherId
    );

    List<TeachingAssignment> findBySubjectId(
            Long subjectId
    );

    List<TeachingAssignment> findBySchoolClassId(
            Long classId
    );

    List<TeachingAssignment> findBySectionId(
            Long sectionId
    );
}