package com.school.schoolmanagementsystem.examinationsubject;

import com.school.schoolmanagementsystem.classmanagement.SchoolClass;
import com.school.schoolmanagementsystem.examination.Examination;
import com.school.schoolmanagementsystem.subject.Subject;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "examination_subjects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "examination_id",
                                "subject_id",
                                "class_id"
                        }
                )
        }
)
public class ExaminationSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "examination_id",
            nullable = false
    )
    private Examination examination;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "subject_id",
            nullable = false
    )
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "class_id",
            nullable = false
    )
    private SchoolClass schoolClass;

    @Column(
            name = "max_marks",
            nullable = false
    )
    private Integer maxMarks;

    @Column(
            name = "pass_marks",
            nullable = false
    )
    private Integer passMarks;

    @Column(
            name = "exam_date",
            nullable = false
    )
    private LocalDate examDate;

    public ExaminationSubject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
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