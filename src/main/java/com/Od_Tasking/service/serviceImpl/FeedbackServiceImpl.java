package com.Od_Tasking.service.serviceImpl;

import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.FeedbackRespone;
import com.Od_Tasking.entity.Feedback;
import com.Od_Tasking.mapper.FeedbackMapper;
import com.Od_Tasking.repository.FeedbackRepository;
import com.Od_Tasking.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public void createFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackMapper.toFeedback(feedbackRequest);
        feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(String feedbackId) {

    }

    @Override
    public void updateFeedback(String feedbackId, String feedbackText, int rating) {

    }

    @Override
    public FeedbackRespone getFeedbackById(String token, String feedbackId) {
        return null;
    }

    @Override
    public List<FeedbackRespone> getAllFeedbacks(String token) {
        return List.of();
    }
}
