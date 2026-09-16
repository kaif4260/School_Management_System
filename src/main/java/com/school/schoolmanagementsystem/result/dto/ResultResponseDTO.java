package com.school.schoolmanagementsystem.result.dto;

import java.util.List;

public class ResultResponseDTO {

    private Long enrollmentId;

    private Long studentId;
    private String studentName;

    private Long classId;
    private String className;

    private Long sectionId;
    private String sectionName;

    private String academicYear;

    private Long examinationId;
    private String examinationName;

    private Integer totalMaxMarks;
    private Integer totalObtainedMarks;

    private Double percentage;

    private String grade;

    private String result;

    private List<SubjectResultDTO> subjects;


    public ResultResponseDTO() {
    }


    public ResultResponseDTO(
            Long enrollmentId,
            Long studentId,
            String studentName,
            Long classId,
            String className,
            Long sectionId,
            String sectionName,
            String academicYear,
            Long examinationId,
            String examinationName,
            Integer totalMaxMarks,
            Integer totalObtainedMarks,
            Double percentage,
            String grade,
            String result,
            List<SubjectResultDTO> subjects) {

        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.classId = classId;
        this.className = className;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.academicYear = academicYear;
        this.examinationId = examinationId;
        this.examinationName = examinationName;
        this.totalMaxMarks = totalMaxMarks;
        this.totalObtainedMarks = totalObtainedMarks;
        this.percentage = percentage;
        this.grade = grade;
        this.result = result;
        this.subjects = subjects;
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

    public Long getExaminationId() {
        return examinationId;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public Integer getTotalMaxMarks() {
        return totalMaxMarks;
    }

    public Integer getTotalObtainedMarks() {
        return totalObtainedMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getGrade() {
        return grade;
    }

    public String getResult() {
        return result;
    }

    public List<SubjectResultDTO> getSubjects() {
        return subjects;
    }
}