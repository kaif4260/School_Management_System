package com.school.schoolmanagementsystem.teachingassignment.dto;

import com.school.schoolmanagementsystem.teachingassignment.AssignmentStatus;

public class TeachingAssignmentResponseDTO {

    private Long id;

    private Long teacherId;
    private Long subjectId;
    private Long classId;
    private Long sectionId;

    private AssignmentStatus status;

    public TeachingAssignmentResponseDTO() {
    }

    public TeachingAssignmentResponseDTO(
            Long id,
            Long teacherId,
            Long subjectId,
            Long classId,
            Long sectionId,
            AssignmentStatus status) {

        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.classId = classId;
        this.sectionId = sectionId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}
