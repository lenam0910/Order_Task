package com.Od_Tasking.controller;


import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackController {
    FeedbackService feedbackService;


    @PostMapping(value = "/submit-feedback", consumes = {"multipart/form-data"})
    public ApiRespone<Object> submitFeedback(
            @RequestBody FeedbackRequest feedbackRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiRespone.builder()
                .code(1000)
                .message("Feedback submitted successfully")
                .result(feedbackService.createFeedback(feedbackRequest, image))
                .build();
    }
}
