package com.Od_Tasking.service;

import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.FeedbackRespone;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FeedbackService {

    FeedbackRespone createFeedback(FeedbackRequest feedbackRequest);
    void deleteFeedback(String feedbackId);
    FeedbackRespone updateFeedback(String feedbackId, String feedbackText, int rating);
    FeedbackRespone getFeedbackById(String token, String feedbackId);
    List<FeedbackRespone> getAllFeedbacks(String token);
}
