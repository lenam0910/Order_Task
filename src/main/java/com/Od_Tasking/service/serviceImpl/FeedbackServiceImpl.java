package com.Od_Tasking.service.serviceImpl;

import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.FeedbackRespone;

import com.Od_Tasking.entity.Feedback;
import com.Od_Tasking.mapper.FeedbackMapper;
import com.Od_Tasking.repository.FeedbackRepository;
import com.Od_Tasking.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public FeedbackRespone createFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackMapper.toFeedback(feedbackRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        }
        feedback.setUserName(username);
        feedback.setOrderId("1");

        feedbackRepository.save(feedback);
        return feedbackMapper.toRespone(feedback);
    }



//    private String saveImage(MultipartFile image) throws IOException {
//        File dir = new File(uploadDir);
//        if (!dir.exists()) {
//            dir.mkdirs(); // Tạo thư mục nếu chưa có
//        }
//        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//        File dest = new File(dir, fileName);
//        image.transferTo(dest);
//        return "/image/" + fileName; // Đường dẫn tương đối để truy cập
//    }

    @Override
    public void deleteFeedback(String feedbackId) {

    }

    @Override
    public FeedbackRespone updateFeedback(String feedbackId, String feedbackText, int rating) {
        return null;
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
