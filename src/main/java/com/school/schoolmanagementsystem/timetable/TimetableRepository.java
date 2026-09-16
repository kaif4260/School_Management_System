package com.school.schoolmanagementsystem.timetable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableRepository
        extends JpaRepository<Timetable, Long> {

    List<Timetable> findBySchoolClassId(
            Long classId
    );

    List<Timetable> findBySectionId(
            Long sectionId
    );

    List<Timetable> findByTeacherId(
            Long teacherId
    );

    List<Timetable> findBySubjectId(
            Long subjectId
    );

    List<Timetable> findByDayOfWeek(
            DayOfWeek dayOfWeek
    );

    List<Timetable> findBySectionIdAndDayOfWeek(
            Long sectionId,
            DayOfWeek dayOfWeek
    );

    List<Timetable> findByRoomNumberAndDayOfWeek(
            String roomNumber,
            DayOfWeek dayOfWeek
    );
}