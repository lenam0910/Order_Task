package com.Od_Tasking.controller;


import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackController {
    FeedbackService feedbackService;


    @PostMapping(value = "/submit-feedback", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiRespone<Object> submitFeedback(
            @RequestBody FeedbackRequest feedbackRequest) {
        return ApiRespone.builder()
                .code(1000)
                .message("Feedback submitted successfully")
                .result(feedbackService.createFeedback(feedbackRequest))
                .build();
    }
}
