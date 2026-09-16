package com.school.schoolmanagementsystem.timetable;

import com.school.schoolmanagementsystem.classmanagement.SchoolClass;
import com.school.schoolmanagementsystem.classmanagement.ClassRepository;
import com.school.schoolmanagementsystem.section.Section;
import com.school.schoolmanagementsystem.section.SectionRepository;
import com.school.schoolmanagementsystem.subject.Subject;
import com.school.schoolmanagementsystem.subject.SubjectRepository;
import com.school.schoolmanagementsystem.teacher.Teacher;
import com.school.schoolmanagementsystem.teacher.TeacherRepository;
import com.school.schoolmanagementsystem.timetable.dto.TimetableRequestDTO;
import com.school.schoolmanagementsystem.timetable.dto.TimetableResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    private final TimetableRepository timetableRepository;

    private final ClassRepository schoolClassRepository;

    private final SectionRepository sectionRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherRepository teacherRepository;


    public TimetableService(
            TimetableRepository timetableRepository,
            ClassRepository schoolClassRepository,
            SectionRepository sectionRepository,
            SubjectRepository subjectRepository,
            TeacherRepository teacherRepository) {

        this.timetableRepository = timetableRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.sectionRepository = sectionRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    // CREATE

    public TimetableResponseDTO create(
            TimetableRequestDTO request) {

        validateRequest(request);


        SchoolClass schoolClass =
                schoolClassRepository
                        .findById(request.getClassId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Class not found with id: "
                                                + request.getClassId()
                                )
                        );


        Section section =
                sectionRepository
                        .findById(request.getSectionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Section not found with id: "
                                                + request.getSectionId()
                                )
                        );


        Subject subject =
                subjectRepository
                        .findById(request.getSubjectId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subject not found with id: "
                                                + request.getSubjectId()
                                )
                        );


        Teacher teacher =
                teacherRepository
                        .findById(request.getTeacherId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Teacher not found with id: "
                                                + request.getTeacherId()
                                )
                        );

        // Validate section belongs to class

        if (!section.getSchoolClass()
                .getId()
                .equals(schoolClass.getId())) {

            throw new IllegalArgumentException(
                    "Section does not belong to the selected class"
            );
        }

        // Check class/section conflict

        validateClassSectionConflict(
                request
        );

        // Check teacher conflict

        validateTeacherConflict(
                request
        );

        // Check room conflict
        validateRoomConflict(request);


        Timetable timetable =
                new Timetable();

        timetable.setSchoolClass(
                schoolClass
        );

        timetable.setSection(
                section
        );

        timetable.setSubject(
                subject
        );

        timetable.setTeacher(
                teacher
        );

        timetable.setDayOfWeek(
                request.getDayOfWeek()
        );

        timetable.setStartTime(
                request.getStartTime()
        );

        timetable.setEndTime(
                request.getEndTime()
        );

        timetable.setRoomNumber(
                request.getRoomNumber()
        );


        Timetable saved =
                timetableRepository.save(
                        timetable
                );


        return convertToResponse(saved);
    }

    // VALIDATE REQUEST

    private void validateRequest(
            TimetableRequestDTO request) {

        if (request.getClassId() == null) {

            throw new IllegalArgumentException(
                    "Class ID is required"
            );
        }

        if (request.getSectionId() == null) {

            throw new IllegalArgumentException(
                    "Section ID is required"
            );
        }

        if (request.getSubjectId() == null) {

            throw new IllegalArgumentException(
                    "Subject ID is required"
            );
        }

        if (request.getTeacherId() == null) {

            throw new IllegalArgumentException(
                    "Teacher ID is required"
            );
        }

        if (request.getDayOfWeek() == null) {

            throw new IllegalArgumentException(
                    "Day of week is required"
            );
        }

        if (request.getStartTime() == null
                || request.getEndTime() == null) {

            throw new IllegalArgumentException(
                    "Start time and end time are required"
            );
        }


        if (!request.getStartTime()
                .isBefore(request.getEndTime())) {

            throw new IllegalArgumentException(
                    "Start time must be before end time"
            );
        }
    }

    // CLASS + SECTION CONFLICT

    private void validateClassSectionConflict(
            TimetableRequestDTO request) {

        List<Timetable> existing =
                timetableRepository
                        .findBySectionIdAndDayOfWeek(
                                request.getSectionId(),
                                request.getDayOfWeek()
                        );


        for (Timetable timetable : existing) {

            boolean overlaps =
                    request.getStartTime()
                            .isBefore(
                                    timetable.getEndTime()
                            )
                            &&
                            request.getEndTime()
                                    .isAfter(
                                            timetable.getStartTime()
                                    );


            if (overlaps) {

                throw new IllegalArgumentException(
                        "Class/Section already has a timetable "
                                + "entry during this time"
                );
            }
        }
    }

    // TEACHER CONFLICT

    private void validateTeacherConflict(
            TimetableRequestDTO request) {

        List<Timetable> existing =
                timetableRepository
                        .findByTeacherId(
                                request.getTeacherId()
                        );


        for (Timetable timetable : existing) {

            if (!timetable.getDayOfWeek()
                    .equals(request.getDayOfWeek())) {

                continue;
            }


            boolean overlaps =
                    request.getStartTime()
                            .isBefore(
                                    timetable.getEndTime()
                            )
                            &&
                            request.getEndTime()
                                    .isAfter(
                                            timetable.getStartTime()
                                    );


            if (overlaps) {

                throw new IllegalArgumentException(
                        "Teacher is already assigned "
                                + "during this time"
                );
            }
        }
    }

    private void validateRoomConflict(
            TimetableRequestDTO request) {

        // Room is optional
        if (request.getRoomNumber() == null
                || request.getRoomNumber().isBlank()) {

            return;
        }

        List<Timetable> existing =
                timetableRepository
                        .findByRoomNumberAndDayOfWeek(
                                request.getRoomNumber(),
                                request.getDayOfWeek()
                        );

        for (Timetable timetable : existing) {

            boolean overlaps =
                    request.getStartTime()
                            .isBefore(
                                    timetable.getEndTime()
                            )
                            &&
                            request.getEndTime()
                                    .isAfter(
                                            timetable.getStartTime()
                                    );

            if (overlaps) {

                throw new IllegalArgumentException(
                        "Room "
                                + request.getRoomNumber()
                                + " is already occupied during this time"
                );
            }
        }
    }

    // CLASS + SECTION CONFLICT

    private void validateClassSectionConflictForUpdate(
            Long id,
            TimetableRequestDTO request) {

        List<Timetable> existing =
                timetableRepository
                        .findBySectionIdAndDayOfWeek(
                                request.getSectionId(),
                                request.getDayOfWeek()
                        );

        for (Timetable timetable : existing) {

            // Ignore the timetable being updated
            // Without this, updating a timetable without changing its time would incorrectly conflict with itself.
            if (timetable.getId().equals(id)) {
                continue;
            }

            boolean overlaps =
                    request.getStartTime()
                            .isBefore(
                                    timetable.getEndTime()
                            )
                            &&
                            request.getEndTime()
                                    .isAfter(
                                            timetable.getStartTime()
                                    );

            if (overlaps) {

                throw new IllegalArgumentException(
                        "Class/Section already has a timetable "
                                + "entry during this time"
                );
            }
        }
    }

    // TEACHER CONFLICT

    private void validateTeacherConflictForUpdate(
            Long id,
            TimetableRequestDTO request) {

        List<Timetable> existing =
                timetableRepository
                        .findByTeacherId(
                                request.getTeacherId()
                        );

        for (Timetable timetable : existing) {

            if (timetable.getId().equals(id)) {
                continue;
            }

            if (!timetable.getDayOfWeek()
                    .equals(request.getDayOfWeek())) {

                continue;
            }

            boolean overlaps =
                    request.getStartTime()
                            .isBefore(
                                    timetable.getEndTime()
                            )
                            &&
                            request.getEndTime()
                                    .isAfter(
                                            timetable.getStartTime()
                                    );

            if (overlaps) {

                throw new IllegalArgumentException(
                        "Teacher is already assigned "
                                + "during this time"
                );
            }
        }
    }

    //Room conflict

    private void validateRoomConflictForUpdate(
            Long id,
            TimetableRequestDTO request) {

        if (request.getRoomNumber() == null
                || request.getRoomNumber().isBlank()) {

            return;
        }

        List<Timetable> existing =
                timetableRepository
                        .findByRoomNumberAndDayOfWeek(
                                request.getRoomNumber(),
                                request.getDayOfWeek()
                        );

        for (Timetable timetable : existing) {
            //Ignore the timetable being updated
            if (timetable.getId().equals(id)) {
                continue;
            }

            boolean overlaps =
                    request.getStartTime()
                            .isBefore(
                                    timetable.getEndTime()
                            )
                            &&
                            request.getEndTime()
                                    .isAfter(
                                            timetable.getStartTime()
                                    );

            if (overlaps) {

                throw new IllegalArgumentException(
                        "Room "
                                + request.getRoomNumber()
                                + " is already occupied during this time"
                );
            }
        }
    }



    // GET ALL

    public List<TimetableResponseDTO> getAll() {

        return timetableRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID

    public TimetableResponseDTO getById(
            Long id) {

        Timetable timetable =
                timetableRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Timetable not found with id: "
                                                + id
                                )
                        );

        return convertToResponse(
                timetable
        );
    }

    // GET BY CLASS

    public List<TimetableResponseDTO> getByClass(
            Long classId) {

        return timetableRepository
                .findBySchoolClassId(classId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY SECTION

    public List<TimetableResponseDTO> getBySection(
            Long sectionId) {

        return timetableRepository
                .findBySectionId(sectionId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY TEACHER

    public List<TimetableResponseDTO> getByTeacher(
            Long teacherId) {

        return timetableRepository
                .findByTeacherId(teacherId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY DAY

    public List<TimetableResponseDTO> getByDay(
            DayOfWeek dayOfWeek) {

        return timetableRepository
                .findByDayOfWeek(dayOfWeek)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY SECTION + DAY

    public List<TimetableResponseDTO>
    getBySectionAndDay(
            Long sectionId,
            DayOfWeek dayOfWeek) {

        return timetableRepository
                .findBySectionIdAndDayOfWeek(
                        sectionId,
                        dayOfWeek
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // DELETE

    public void delete(Long id) {

        if (!timetableRepository.existsById(id)) {

            throw new RuntimeException(
                    "Timetable not found with id: "
                            + id
            );
        }

        timetableRepository.deleteById(id);
    }

    // ENTITY → RESPONSE DTO

    private TimetableResponseDTO convertToResponse(
            Timetable timetable) {

        SchoolClass schoolClass =
                timetable.getSchoolClass();

        Section section =
                timetable.getSection();

        Subject subject =
                timetable.getSubject();

        Teacher teacher =
                timetable.getTeacher();


        return new TimetableResponseDTO(

                timetable.getId(),

                schoolClass.getId(),
                schoolClass.getName(),

                section.getId(),
                section.getName(),

                subject.getId(),
                subject.getName(),

                teacher.getId(),
                teacher.getFirstName() + " " + teacher.getLastName(),

                timetable.getDayOfWeek(),

                timetable.getStartTime(),

                timetable.getEndTime(),

                timetable.getRoomNumber()
        );
    }

    //UPDATE VALIDATION
    public TimetableResponseDTO update(
            Long id,
            TimetableRequestDTO request) {

        validateRequest(request);


        Timetable timetable =
                timetableRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Timetable not found with id: "
                                                + id
                                )
                        );


        SchoolClass schoolClass =
                schoolClassRepository
                        .findById(request.getClassId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Class not found with id: "
                                                + request.getClassId()
                                )
                        );


        Section section =
                sectionRepository
                        .findById(request.getSectionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Section not found with id: "
                                                + request.getSectionId()
                                )
                        );


        Subject subject =
                subjectRepository
                        .findById(request.getSubjectId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subject not found with id: "
                                                + request.getSubjectId()
                                )
                        );


        Teacher teacher =
                teacherRepository
                        .findById(request.getTeacherId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Teacher not found with id: "
                                                + request.getTeacherId()
                                )
                        );


        // Validate section belongs to class

        if (!section.getSchoolClass()
                .getId()
                .equals(schoolClass.getId())) {

            throw new IllegalArgumentException(
                    "Section does not belong to the selected class"
            );
        }


        // Check conflicts while ignoring current timetable

        validateClassSectionConflictForUpdate(
                id,
                request
        );

        validateTeacherConflictForUpdate(
                id,
                request
        );

        validateRoomConflictForUpdate(
                id,
                request
        );


        // Update entity

        timetable.setSchoolClass(
                schoolClass
        );

        timetable.setSection(
                section
        );

        timetable.setSubject(
                subject
        );

        timetable.setTeacher(
                teacher
        );

        timetable.setDayOfWeek(
                request.getDayOfWeek()
        );

        timetable.setStartTime(
                request.getStartTime()
        );

        timetable.setEndTime(
                request.getEndTime()
        );

        timetable.setRoomNumber(
                request.getRoomNumber()
        );


        Timetable updated =
                timetableRepository.save(
                        timetable
                );


        return convertToResponse(updated);
    }
}