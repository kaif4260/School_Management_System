package com.school.schoolmanagementsystem.announcement;

import com.school.schoolmanagementsystem.announcement.dto.AnnouncementRequestDTO;
import com.school.schoolmanagementsystem.announcement.dto.AnnouncementResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(
            AnnouncementRepository announcementRepository) {

        this.announcementRepository =
                announcementRepository;
    }

    // CREATE ANNOUNCEMENT

    public AnnouncementResponseDTO create(
            AnnouncementRequestDTO request) {

        validateRequest(request);

        Announcement announcement =
                new Announcement();

        announcement.setTitle(
                request.getTitle()
        );

        announcement.setDescription(
                request.getDescription()
        );

        announcement.setTargetAudience(
                request.getTargetAudience()
        );

        // New announcements are unpublished
        announcement.setPublished(false);

        // Set creation time
        announcement.setCreatedAt(
                LocalDateTime.now()
        );

        announcement.setPublishedAt(null);

        Announcement saved =
                announcementRepository.save(
                        announcement
                );

        return convertToResponse(saved);
    }

    // GET ALL

    public List<AnnouncementResponseDTO> getAll() {

        return announcementRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID

    public AnnouncementResponseDTO getById(
            Long id) {

        Announcement announcement =
                announcementRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found with id: "
                                                + id
                                )
                        );

        return convertToResponse(
                announcement
        );
    }

    // GET PUBLISHED ANNOUNCEMENTS

    public List<AnnouncementResponseDTO>
    getPublished() {

        return announcementRepository
                .findByPublishedTrue()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY AUDIENCE

    public List<AnnouncementResponseDTO>
    getByAudience(
            AnnouncementAudience audience) {

        return announcementRepository
                .findByTargetAudience(audience)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET PUBLISHED BY AUDIENCE

    public List<AnnouncementResponseDTO>
    getPublishedByAudience(
            AnnouncementAudience audience) {

        return announcementRepository
                .findByPublishedTrueAndTargetAudience(
                        audience
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE

    public AnnouncementResponseDTO update(
            Long id,
            AnnouncementRequestDTO request) {

        validateRequest(request);

        Announcement announcement =
                announcementRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found with id: "
                                                + id
                                )
                        );

        announcement.setTitle(
                request.getTitle()
        );

        announcement.setDescription(
                request.getDescription()
        );

        announcement.setTargetAudience(
                request.getTargetAudience()
        );

        Announcement updated =
                announcementRepository.save(
                        announcement
                );

        return convertToResponse(updated);
    }

    // PUBLISH

    public AnnouncementResponseDTO publish(
            Long id) {

        Announcement announcement =
                announcementRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found with id: "
                                                + id
                                )
                        );

        announcement.setPublished(true);

        announcement.setPublishedAt(
                LocalDateTime.now()
        );

        Announcement updated =
                announcementRepository.save(
                        announcement
                );

        return convertToResponse(updated);
    }

    // UNPUBLISH

    public AnnouncementResponseDTO unpublish(
            Long id) {

        Announcement announcement =
                announcementRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found with id: "
                                                + id
                                )
                        );

        announcement.setPublished(false);

        announcement.setPublishedAt(null);

        Announcement updated =
                announcementRepository.save(
                        announcement
                );

        return convertToResponse(updated);
    }

    // DELETE

    public void delete(Long id) {

        if (!announcementRepository.existsById(id)) {

            throw new RuntimeException(
                    "Announcement not found with id: "
                            + id
            );
        }

        announcementRepository.deleteById(id);
    }

    // VALIDATION

    private void validateRequest(
            AnnouncementRequestDTO request) {

        if (request.getTitle() == null
                || request.getTitle().isBlank()) {

            throw new IllegalArgumentException(
                    "Announcement title is required"
            );
        }

        if (request.getTitle().length() > 200) {

            throw new IllegalArgumentException(
                    "Announcement title cannot exceed 200 characters"
            );
        }

        if (request.getDescription() == null
                || request.getDescription().isBlank()) {

            throw new IllegalArgumentException(
                    "Announcement description is required"
            );
        }

        if (request.getTargetAudience() == null) {

            throw new IllegalArgumentException(
                    "Target audience is required"
            );
        }
    }

    // ENTITY → RESPONSE DTO

    private AnnouncementResponseDTO convertToResponse(
            Announcement announcement) {

        return new AnnouncementResponseDTO(

                announcement.getId(),

                announcement.getTitle(),

                announcement.getDescription(),

                announcement.getTargetAudience(),

                announcement.isPublished(),

                announcement.getCreatedAt(),

                announcement.getPublishedAt()
        );
    }
}