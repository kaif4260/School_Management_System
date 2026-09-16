package com.school.schoolmanagementsystem.announcement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository
        extends JpaRepository<Announcement, Long> {

    List<Announcement> findByPublishedTrue();

    List<Announcement> findByTargetAudience(
            AnnouncementAudience targetAudience
    );

    List<Announcement> findByPublishedTrueAndTargetAudience(
            AnnouncementAudience targetAudience
    );
}