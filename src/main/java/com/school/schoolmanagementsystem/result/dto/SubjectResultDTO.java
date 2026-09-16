package com.school.schoolmanagementsystem.result.dto;

public class SubjectResultDTO {

    private Long subjectId;
    private String subjectName;

    private Integer maxMarks;
    private Integer passMarks;
    private Integer obtainedMarks;

    private Double percentage;
    private String grade;
    private String result;


    public SubjectResultDTO() {
    }


    public SubjectResultDTO(
            Long subjectId,
            String subjectName,
            Integer maxMarks,
            Integer passMarks,
            Integer obtainedMarks,
            Double percentage,
            String grade,
            String result) {

        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.maxMarks = maxMarks;
        this.passMarks = passMarks;
        this.obtainedMarks = obtainedMarks;
        this.percentage = percentage;
        this.grade = grade;
        this.result = result;
    }


    public Long getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
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

    public String getResult() {
        return result;
    }
}