package com.Od_Tasking.service.serviceImpl;

import com.Od_Tasking.common.Utils;
import com.Od_Tasking.config.MinioConfig;
import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.FeedbackRespone;

import com.Od_Tasking.entity.Feedback;
import com.Od_Tasking.mapper.FeedbackMapper;
import com.Od_Tasking.repository.FeedbackRepository;
import com.Od_Tasking.service.FeedbackService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

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
        Feedback feedback = feedbackMapper.toFeedback(feedbackRequest);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = Utils.getUserName();
        feedback.setUserName(username);

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            feedback.setImgFeedback(imagePath);
        }
        feedbackRepository.save(feedback);
        return feedbackMapper.toRespone(feedback);
    }

    @Override
    public String sendFeedbackImg(MultipartFile image) {
        try {
            if (image == null || image.isEmpty()) {
                throw new IllegalArgumentException("Image file is required");
            }

            String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .stream(image.getInputStream(), image.getSize(), -1)
                            .contentType(image.getContentType())
                            .build()
            );

            return String.format("%s/%s/%s", minioConfig.getUrl(), minioConfig.getBucketName(), fileName);
        } catch (Exception e) {
            log.error("Error uploading image to MinIO: {}", e.getMessage());
            throw new RuntimeException("Failed to upload image", e);
        }
    }



    @Override
    public InputStream getFeedbackImg(String fileName) {
        try {
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new IllegalArgumentException("File name is required");
            }

            // Lấy object từ MinIO
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .build();

            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            log.error("Error retrieving image from MinIO: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve image", e);
        }
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
