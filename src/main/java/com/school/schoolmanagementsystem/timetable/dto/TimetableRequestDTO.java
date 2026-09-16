package com.school.schoolmanagementsystem.timetable.dto;

import com.school.schoolmanagementsystem.timetable.DayOfWeek;

import java.time.LocalTime;

public class TimetableRequestDTO {

    private Long classId;

    private Long sectionId;

    private Long subjectId;

    private Long teacherId;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private String roomNumber;


    public TimetableRequestDTO() {
    }


    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }


    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }


    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }


    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }


    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(
            DayOfWeek dayOfWeek) {

        this.dayOfWeek = dayOfWeek;
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(
            LocalTime startTime) {

        this.startTime = startTime;
    }


    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(
            LocalTime endTime) {

        this.endTime = endTime;
    }


    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(
            String roomNumber) {

        this.roomNumber = roomNumber;
    }
}