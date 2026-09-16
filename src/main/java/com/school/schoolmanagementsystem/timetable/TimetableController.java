package com.school.schoolmanagementsystem.timetable;

import com.school.schoolmanagementsystem.timetable.dto.TimetableRequestDTO;
import com.school.schoolmanagementsystem.timetable.dto.TimetableResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(
            TimetableService timetableService) {

        this.timetableService = timetableService;
    }


    // CREATE TIMETABLE
    @PostMapping
    public TimetableResponseDTO create(
            @RequestBody TimetableRequestDTO request) {

        return timetableService.create(request);
    }


    // GET ALL TIMETABLES
    @GetMapping
    public List<TimetableResponseDTO> getAll() {

        return timetableService.getAll();
    }


    // GET TIMETABLE BY ID
    @GetMapping("/{id}")
    public TimetableResponseDTO getById(
            @PathVariable Long id) {

        return timetableService.getById(id);
    }


    // GET BY CLASS
    @GetMapping("/class/{classId}")
    public List<TimetableResponseDTO> getByClass(
            @PathVariable Long classId) {

        return timetableService.getByClass(classId);
    }


    // GET BY SECTION
    @GetMapping("/section/{sectionId}")
    public List<TimetableResponseDTO> getBySection(
            @PathVariable Long sectionId) {

        return timetableService.getBySection(sectionId);
    }


    // GET BY TEACHER
    @GetMapping("/teacher/{teacherId}")
    public List<TimetableResponseDTO> getByTeacher(
            @PathVariable Long teacherId) {

        return timetableService.getByTeacher(teacherId);
    }


    // GET BY DAY
    @GetMapping("/day/{dayOfWeek}")
    public List<TimetableResponseDTO> getByDay(
            @PathVariable DayOfWeek dayOfWeek) {

        return timetableService.getByDay(dayOfWeek);
    }


    // GET BY SECTION + DAY
    @GetMapping("/section/{sectionId}/day/{dayOfWeek}")
    public List<TimetableResponseDTO> getBySectionAndDay(
            @PathVariable Long sectionId,
            @PathVariable DayOfWeek dayOfWeek) {

        return timetableService.getBySectionAndDay(
                sectionId,
                dayOfWeek
        );
    }

    @PutMapping("/{id}")
    public TimetableResponseDTO update(
            @PathVariable Long id,
            @RequestBody TimetableRequestDTO request) {

        return timetableService.update(
                id,
                request
        );
    }


    // DELETE
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id) {

        timetableService.delete(id);

        return "Timetable deleted successfully";
    }
}