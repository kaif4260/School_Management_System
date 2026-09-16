package com.school.schoolmanagementsystem.examination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExaminationRepository
        extends JpaRepository<Examination, Long> {

    boolean existsByNameIgnoreCaseAndAcademicYear(
            String name,
            String academicYear
    );

    List<Examination> findByAcademicYear(
            String academicYear
    );

    List<Examination> findByStatus(
            ExaminationStatus status
    );
}