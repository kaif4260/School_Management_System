package com.school.schoolmanagementsystem.announcement.dto;

import com.school.schoolmanagementsystem.announcement.AnnouncementAudience;

public class AnnouncementRequestDTO {

    private String title;

    private String description;

    private AnnouncementAudience targetAudience;


    public AnnouncementRequestDTO() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public AnnouncementAudience getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(
            AnnouncementAudience targetAudience) {

        this.targetAudience = targetAudience;
    }
}