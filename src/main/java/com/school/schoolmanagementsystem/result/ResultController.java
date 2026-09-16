package com.school.schoolmanagementsystem.result;

import com.school.schoolmanagementsystem.result.dto.ResultResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/enrollment/{enrollmentId}/examination/{examinationId}")
    public ResultResponseDTO getResult(
            @PathVariable Long enrollmentId,
            @PathVariable Long examinationId) {

        return resultService.getResult(
                enrollmentId,
                examinationId
        );
    }
}