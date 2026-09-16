package com.school.schoolmanagementsystem.result;

import com.school.schoolmanagementsystem.enrollment.StudentEnrollment;
import com.school.schoolmanagementsystem.enrollment.StudentEnrollmentRepository;
import com.school.schoolmanagementsystem.examination.Examination;
import com.school.schoolmanagementsystem.examination.ExaminationRepository;
import com.school.schoolmanagementsystem.examinationsubject.ExaminationSubject;
import com.school.schoolmanagementsystem.studentmarks.StudentMarks;
import com.school.schoolmanagementsystem.studentmarks.StudentMarksRepository;
import com.school.schoolmanagementsystem.result.dto.ResultResponseDTO;
import com.school.schoolmanagementsystem.result.dto.SubjectResultDTO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;

    private final StudentMarksRepository studentMarksRepository;

    private final ExaminationRepository examinationRepository;


    public ResultService(
            StudentEnrollmentRepository studentEnrollmentRepository,
            StudentMarksRepository studentMarksRepository,
            ExaminationRepository examinationRepository) {

        this.studentEnrollmentRepository =
                studentEnrollmentRepository;

        this.studentMarksRepository =
                studentMarksRepository;

        this.examinationRepository =
                examinationRepository;
    }


    // =========================================================
    // GET RESULT BY ENROLLMENT + EXAMINATION
    // =========================================================

    public ResultResponseDTO getResult(
            Long enrollmentId,
            Long examinationId) {

        // -----------------------------------------------------
        // 1. Find enrollment
        // -----------------------------------------------------

        StudentEnrollment enrollment =
                studentEnrollmentRepository
                        .findById(enrollmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Enrollment not found with id: "
                                                + enrollmentId
                                )
                        );


        // -----------------------------------------------------
        // 2. Find examination
        // -----------------------------------------------------

        Examination examination =
                examinationRepository
                        .findById(examinationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Examination not found with id: "
                                                + examinationId
                                )
                        );


        // -----------------------------------------------------
        // 3. Get marks belonging to this enrollment
        // -----------------------------------------------------

        List<StudentMarks> marksList =
                studentMarksRepository
                        .findByEnrollmentId(enrollmentId);


        // -----------------------------------------------------
        // 4. Keep only marks for requested examination
        // -----------------------------------------------------

        List<StudentMarks> examinationMarks =
                marksList
                        .stream()
                        .filter(marks ->
                                marks
                                        .getExaminationSubject()
                                        .getExamination()
                                        .getId()
                                        .equals(examinationId)
                        )
                        .toList();


        if (examinationMarks.isEmpty()) {

            throw new RuntimeException(
                    "No marks found for enrollment id: "
                            + enrollmentId
                            + " and examination id: "
                            + examinationId
            );
        }


        // -----------------------------------------------------
        // 5. Prepare subject results
        // -----------------------------------------------------

        List<SubjectResultDTO> subjectResults =
                new ArrayList<>();


        int totalMaxMarks = 0;

        int totalObtainedMarks = 0;

        boolean overallPass = true;


        // -----------------------------------------------------
        // 6. Calculate each subject
        // -----------------------------------------------------

        for (StudentMarks marks : examinationMarks) {

            ExaminationSubject examinationSubject =
                    marks.getExaminationSubject();


            int maxMarks =
                    examinationSubject.getMaxMarks();

            int passMarks =
                    examinationSubject.getPassMarks();

            int obtainedMarks =
                    marks.getObtainedMarks();


            double percentage =
                    ((double) obtainedMarks
                            / maxMarks)
                            * 100;


            String subjectResult;

            if (obtainedMarks >= passMarks) {

                subjectResult = "PASS";

            } else {

                subjectResult = "FAIL";

                overallPass = false;
            }


            String grade =
                    calculateGrade(percentage);


            totalMaxMarks += maxMarks;

            totalObtainedMarks += obtainedMarks;


            SubjectResultDTO subjectResultDTO =
                    new SubjectResultDTO(

                            examinationSubject
                                    .getSubject()
                                    .getId(),

                            examinationSubject
                                    .getSubject()
                                    .getName(),

                            maxMarks,

                            passMarks,

                            obtainedMarks,

                            round(percentage),

                            grade,

                            subjectResult
                    );


            subjectResults.add(
                    subjectResultDTO
            );
        }


        // -----------------------------------------------------
        // 7. Calculate overall percentage
        // -----------------------------------------------------

        double overallPercentage =
                ((double) totalObtainedMarks
                        / totalMaxMarks)
                        * 100;


        // -----------------------------------------------------
        // 8. Calculate overall grade
        // -----------------------------------------------------

        String overallGrade =
                calculateGrade(
                        overallPercentage
                );


        // -----------------------------------------------------
        // 9. Overall result
        // -----------------------------------------------------

        String overallResult =
                overallPass
                        ? "PASS"
                        : "FAIL";


        // -----------------------------------------------------
        // 10. Student name
        // -----------------------------------------------------

        String studentName =
                enrollment
                        .getStudent()
                        .getFirstName()
                        + " "
                        + enrollment
                        .getStudent()
                        .getLastName();


        // -----------------------------------------------------
        // 11. Build response
        // -----------------------------------------------------

        return new ResultResponseDTO(

                enrollment.getId(),

                enrollment
                        .getStudent()
                        .getId(),

                studentName,

                enrollment
                        .getSchoolClass()
                        .getId(),

                enrollment
                        .getSchoolClass()
                        .getName(),

                enrollment
                        .getSection()
                        .getId(),

                enrollment
                        .getSection()
                        .getName(),

                enrollment.getAcademicYear(),

                examination.getId(),

                examination.getName(),

                totalMaxMarks,

                totalObtainedMarks,

                round(overallPercentage),

                overallGrade,

                overallResult,

                subjectResults
        );
    }


    // =========================================================
    // GRADE CALCULATION
    // =========================================================

    private String calculateGrade(
            double percentage) {

        if (percentage >= 90) {
            return "A+";
        }

        if (percentage >= 80) {
            return "A";
        }

        if (percentage >= 70) {
            return "B+";
        }

        if (percentage >= 60) {
            return "B";
        }

        if (percentage >= 50) {
            return "C";
        }

        if (percentage >= 40) {
            return "D";
        }

        return "F";
    }


    // =========================================================
    // ROUND DECIMAL
    // =========================================================

    private double round(double value) {

        return Math.round(
                value * 100.0
        ) / 100.0;
    }
}