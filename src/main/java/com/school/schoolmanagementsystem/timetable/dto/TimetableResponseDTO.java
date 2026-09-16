package com.school.schoolmanagementsystem.timetable.dto;

import com.school.schoolmanagementsystem.timetable.DayOfWeek;

import java.time.LocalTime;

public class TimetableResponseDTO {

    private Long id;

    private Long classId;
    private String className;

    private Long sectionId;
    private String sectionName;

    private Long subjectId;
    private String subjectName;

    private Long teacherId;
    private String teacherName;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private String roomNumber;


    public TimetableResponseDTO() {
    }


    public TimetableResponseDTO(
            Long id,
            Long classId,
            String className,
            Long sectionId,
            String sectionName,
            Long subjectId,
            String subjectName,
            Long teacherId,
            String teacherName,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            String roomNumber) {

        this.id = id;
        this.classId = classId;
        this.className = className;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNumber = roomNumber;
    }


    public Long getId() {
        return id;
    }

    public Long getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}