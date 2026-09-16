package com.school.schoolmanagementsystem.announcement.dto;

import com.school.schoolmanagementsystem.announcement.AnnouncementAudience;

import java.time.LocalDateTime;

public class AnnouncementResponseDTO {

    private Long id;

    private String title;

    private String description;

    private AnnouncementAudience targetAudience;

    private boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;


    public AnnouncementResponseDTO() {
    }


    public AnnouncementResponseDTO(
            Long id,
            String title,
            String description,
            AnnouncementAudience targetAudience,
            boolean published,
            LocalDateTime createdAt,
            LocalDateTime publishedAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.targetAudience = targetAudience;
        this.published = published;
        this.createdAt = createdAt;
        this.publishedAt = publishedAt;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public AnnouncementAudience getTargetAudience() {
        return targetAudience;
    }

    public boolean isPublished() {
        return published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}