package com.school.schoolmanagementsystem.studentmarks.dto;

import java.time.LocalDate;

public class StudentMarksResponseDTO {

    private final Long id;

    private final Long enrollmentId;

    private final Long studentId;
    private final String studentName;

    private final Long examinationId;
    private final String examinationName;

    private final Long examinationSubjectId;

    private final Long subjectId;
    private final String subjectName;

    private final Long classId;
    private final String className;

    private final Long sectionId;
    private final String sectionName;

    private final String academicYear;

    private final Integer maxMarks;
    private final Integer passMarks;
    private final Integer obtainedMarks;

    private final Double percentage;
    private final String grade;

    private final LocalDate examDate;


    public StudentMarksResponseDTO(
            Long id,
            Long enrollmentId,
            Long studentId,
            String studentName,
            Long examinationId,
            String examinationName,
            Long examinationSubjectId,
            Long subjectId,
            String subjectName,
            Long classId,
            String className,
            Long sectionId,
            String sectionName,
            String academicYear,
            Integer maxMarks,
            Integer passMarks,
            Integer obtainedMarks,
            Double percentage,
            String grade,
            LocalDate examDate) {

        this.id = id;
        this.enrollmentId = enrollmentId;

        this.studentId = studentId;
        this.studentName = studentName;

        this.examinationId = examinationId;
        this.examinationName = examinationName;

        this.examinationSubjectId =
                examinationSubjectId;

        this.subjectId = subjectId;
        this.subjectName = subjectName;

        this.classId = classId;
        this.className = className;

        this.sectionId = sectionId;
        this.sectionName = sectionName;

        this.academicYear = academicYear;

        this.maxMarks = maxMarks;
        this.passMarks = passMarks;
        this.obtainedMarks = obtainedMarks;

        this.percentage = percentage;
        this.grade = grade;

        this.examDate = examDate;
    }


    public Long getId() {
        return id;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public Long getExaminationSubjectId() {
        return examinationSubjectId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
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

    public String getAcademicYear() {
        return academicYear;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public Integer getPassMarks() {
        return passMarks;
    }

    public Integer getObtainedMarks() {
        return obtainedMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getGrade() {
        return grade;
    }

    public LocalDate getExamDate() {
        return examDate;
    }
}