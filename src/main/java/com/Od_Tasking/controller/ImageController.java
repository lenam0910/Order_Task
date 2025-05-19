package com.Od_Tasking.controller;

import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.dto.Respone.ImageResponse;
import com.Od_Tasking.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/image")
public class ImageController {
        ImageService imageService;


        @PostMapping("/check_expired")
        public ResponseEntity<String> checkExpired() {
            imageService.deleteImageExpired();
            return ResponseEntity.ok("Delete expired image success");
        }

        @PostMapping("/download/{id}")
        public ApiRespone<Object> uploadImage(@PathVariable String id) {
            return ApiRespone.builder()
                    .code(1000)
                    .message("Download image success")
                    .result(imageService.downloadImage(id))
                    .build();
        }



}
