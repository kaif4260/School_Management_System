package com.school.schoolmanagementsystem.classmanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository
        extends JpaRepository<SchoolClass, Long> {

    Optional<SchoolClass> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(
            String name,
            Long id
    );

    Page<SchoolClass> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );
}
