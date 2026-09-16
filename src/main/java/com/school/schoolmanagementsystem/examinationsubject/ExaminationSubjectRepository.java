package com.school.schoolmanagementsystem.examinationsubject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExaminationSubjectRepository
        extends JpaRepository<ExaminationSubject, Long> {

    boolean existsByExaminationIdAndSubjectIdAndSchoolClassId(
            Long examinationId,
            Long subjectId,
            Long classId
    );

    List<ExaminationSubject>
    findByExaminationId(Long examinationId);

    List<ExaminationSubject>
    findBySubjectId(Long subjectId);

    List<ExaminationSubject>
    findBySchoolClassId(Long classId);

    List<ExaminationSubject>
    findByExaminationIdAndSchoolClassId(
            Long examinationId,
            Long classId
    );
}