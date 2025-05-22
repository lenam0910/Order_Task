package com.Od_Tasking.controller;

import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackController {
    FeedbackService feedbackService;

    @PostMapping
    public ApiRespone<Object> submitFeedback(
           @ModelAttribute(value = "feedback") FeedbackRequest feedbackRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ApiRespone.builder()
                .code(1000)
                .message("Feedback submitted successfully")
                .result(feedbackService.createFeedback(feedbackRequest, image))
                .build();
    }

    @PostMapping(value = "/feedback_img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<String> submitFeedbackWithImage(
            @RequestPart("image") MultipartFile image) throws IOException {
        String imageUrl = feedbackService.sendFeedbackImg(image);
        return ApiRespone.<String>builder()
                .code(1000)
                .message("Image uploaded successfully")
                .result(imageUrl)
                .build();
    }
}