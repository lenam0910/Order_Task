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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;


    private String saveImage(MultipartFile image) throws IOException {
        // Tạo thư mục upload nếu chưa tồn tại
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Tạo tên file duy nhất
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Lưu file vào thư mục
        Files.copy(image.getInputStream(), filePath);

        // Trả về đường dẫn tương đối
        return uploadDir + "/" + fileName;
    }


    @Override
    public FeedbackRespone createFeedback(FeedbackRequest feedbackRequest, MultipartFile image) throws IOException {
// Map DTO sang entity
        Feedback feedback = feedbackMapper.toFeedback(feedbackRequest);

        // Lấy thông tin người dùng từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        }
        feedback.setUserName(username);

        // Xử lý upload ảnh nếu có
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            feedback.setImgFeedback(imagePath); // Lưu đường dẫn ảnh vào entity
        }

        // Lưu feedback vào database
        feedbackRepository.save(feedback);

        // Map entity sang DTO trả về
        return feedbackMapper.toRespone(feedback);
    }

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
