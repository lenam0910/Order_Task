package com.Od_Tasking.controller;

import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackController {
    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);
    FeedbackService feedbackService;

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<Object> submitFeedback(
            @RequestPart("feedback") FeedbackRequest feedbackRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException, IOException {
        return ApiRespone.builder()
                .code(1000)
                .message("Feedback submitted successfully")
                .result(feedbackService.createFeedback(feedbackRequest, image))
                .build();
    }


}