package com.school.schoolmanagementsystem.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository
        extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmployeeId(String employeeId);

    Optional<Teacher> findByEmail(String email);

    Optional<Teacher> findByPhone(String phone);

    boolean existsByEmployeeId(String employeeId);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmployeeIdAndIdNot(
            String employeeId,
            Long id
    );

    boolean existsByEmailAndIdNot(
            String email,
            Long id
    );

    boolean existsByPhoneAndIdNot(
            String phone,
            Long id
    );

    Page<Teacher>
    findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName,
            Pageable pageable
    );
}
