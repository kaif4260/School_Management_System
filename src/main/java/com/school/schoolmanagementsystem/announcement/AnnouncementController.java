package com.school.schoolmanagementsystem.announcement;

import com.school.schoolmanagementsystem.announcement.dto.AnnouncementRequestDTO;
import com.school.schoolmanagementsystem.announcement.dto.AnnouncementResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(
            AnnouncementService announcementService) {

        this.announcementService =
                announcementService;
    }


    // CREATE
    @PostMapping
    public AnnouncementResponseDTO create(
            @RequestBody AnnouncementRequestDTO request) {

        return announcementService.create(request);
    }


    // GET ALL
    @GetMapping
    public List<AnnouncementResponseDTO> getAll() {

        return announcementService.getAll();
    }


    // GET BY ID
    @GetMapping("/{id}")
    public AnnouncementResponseDTO getById(
            @PathVariable Long id) {

        return announcementService.getById(id);
    }


    // GET PUBLISHED
    @GetMapping("/published")
    public List<AnnouncementResponseDTO> getPublished() {

        return announcementService.getPublished();
    }


    // GET BY AUDIENCE
    @GetMapping("/audience/{audience}")
    public List<AnnouncementResponseDTO> getByAudience(
            @PathVariable AnnouncementAudience audience) {

        return announcementService.getByAudience(
                audience
        );
    }


    // GET PUBLISHED BY AUDIENCE
    @GetMapping("/published/audience/{audience}")
    public List<AnnouncementResponseDTO>
    getPublishedByAudience(
            @PathVariable AnnouncementAudience audience) {

        return announcementService
                .getPublishedByAudience(audience);
    }


    // UPDATE
    @PutMapping("/{id}")
    public AnnouncementResponseDTO update(
            @PathVariable Long id,
            @RequestBody AnnouncementRequestDTO request) {

        return announcementService.update(
                id,
                request
        );
    }


    // PUBLISH
    @PutMapping("/{id}/publish")
    public AnnouncementResponseDTO publish(
            @PathVariable Long id) {

        return announcementService.publish(id);
    }


    // UNPUBLISH
    @PutMapping("/{id}/unpublish")
    public AnnouncementResponseDTO unpublish(
            @PathVariable Long id) {

        return announcementService.unpublish(id);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id) {

        announcementService.delete(id);

        return "Announcement deleted successfully";
    }
}