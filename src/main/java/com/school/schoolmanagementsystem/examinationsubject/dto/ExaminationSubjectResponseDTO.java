package com.school.schoolmanagementsystem.examinationsubject.dto;

import java.time.LocalDate;

public class ExaminationSubjectResponseDTO {

    private Long id;

    private Long examinationId;
    private String examinationName;

    private Long subjectId;
    private String subjectName;

    private Long classId;
    private String className;

    private Integer maxMarks;
    private Integer passMarks;

    private LocalDate examDate;

    public ExaminationSubjectResponseDTO() {
    }

    public ExaminationSubjectResponseDTO(
            Long id,
            Long examinationId,
            String examinationName,
            Long subjectId,
            String subjectName,
            Long classId,
            String className,
            Integer maxMarks,
            Integer passMarks,
            LocalDate examDate) {

        this.id = id;

        this.examinationId = examinationId;
        this.examinationName = examinationName;

        this.subjectId = subjectId;
        this.subjectName = subjectName;

        this.classId = classId;
        this.className = className;

        this.maxMarks = maxMarks;
        this.passMarks = passMarks;

        this.examDate = examDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Long examinationId) {
        this.examinationId = examinationId;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Integer getPassMarks() {
        return passMarks;
    }

    public void setPassMarks(Integer passMarks) {
        this.passMarks = passMarks;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
}