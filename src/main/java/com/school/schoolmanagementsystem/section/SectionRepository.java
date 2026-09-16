package com.school.schoolmanagementsystem.section;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository
        extends JpaRepository<Section, Long> {

    boolean existsByNameIgnoreCaseAndSchoolClassId(
            String name,
            Long classId
    );

    boolean existsByNameIgnoreCaseAndSchoolClassIdAndIdNot(
            String name,
            Long classId,
            Long id
    );

    List<Section> findBySchoolClassId(Long classId);

    Page<Section> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Section> findBySchoolClassId(
            Long classId,
            Pageable pageable
    );
}
