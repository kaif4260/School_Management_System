package com.school.schoolmanagementsystem.subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository
        extends JpaRepository<Subject, Long> {

    Optional<Subject> findByCodeIgnoreCase(String code);

    Optional<Subject> findByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCaseAndIdNot(
            String code,
            Long id
    );

    boolean existsByNameIgnoreCaseAndIdNot(
            String name,
            Long id
    );

    Page<Subject> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );
}