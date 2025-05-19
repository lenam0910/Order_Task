package com.Od_Tasking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageCleanupScheduler {
    ImageService imageService;

    @Scheduled(fixedRate = 3600000) // Chạy mỗi 1 giờ (3600000 ms)
    public void cleanupExpiredImages() {
        imageService.deleteImageExpired();
    }
}