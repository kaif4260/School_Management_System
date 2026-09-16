package com.school.schoolmanagementsystem.studentmarks;

import com.school.schoolmanagementsystem.enrollment.StudentEnrollment;
import com.school.schoolmanagementsystem.examinationsubject.ExaminationSubject;
import jakarta.persistence.*;

@Entity
@Table(
        name = "student_marks",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "enrollment_id",
                                "examination_subject_id"
                        }
                )
        }
)
public class StudentMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "enrollment_id",
            nullable = false
    )
    private StudentEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "examination_subject_id",
            nullable = false
    )
    private ExaminationSubject examinationSubject;

    @Column(
            name = "obtained_marks",
            nullable = false
    )
    private Integer obtainedMarks;

    public StudentMarks() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(StudentEnrollment enrollment) {
        this.enrollment = enrollment;
    }

    public ExaminationSubject getExaminationSubject() {
        return examinationSubject;
    }

    public void setExaminationSubject(
            ExaminationSubject examinationSubject) {
        this.examinationSubject = examinationSubject;
    }

    public Integer getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(Integer obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }
}