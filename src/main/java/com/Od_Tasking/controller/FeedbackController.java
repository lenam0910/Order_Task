package com.Od_Tasking.controller;


import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackController {
    FeedbackService feedbackService;


    @PostMapping("/submit-feedback")
    public ApiRespone<Object> submitFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        return ApiRespone.builder()
                .code(1000)
                .message("Feedback submitted successfully")
                .result(feedbackService.createFeedback(feedbackRequest))
                .build();
    }
}
