package com.Od_Tasking.service.serviceImpl;

import com.Od_Tasking.dto.Request.ImageRequest;
import com.Od_Tasking.dto.Respone.ImageResponse;
import com.Od_Tasking.entity.Image;
import com.Od_Tasking.mapper.ImageMapper;
import com.Od_Tasking.repository.ImageRepository;
import com.Od_Tasking.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public ImageResponse getImageById(String id)  {
        return null;
    }

    @Override
    public ImageResponse saveImage(ImageRequest imageRequest,String orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        }

        Image imageOrder = imageMapper.toImage(imageRequest);
        imageOrder.setOrderId(orderId);
        log.info("Image is saved with id {}", imageOrder.getId());
        imageOrder.setUserName(username);

        Image image = imageRepository.save(imageOrder);

        return imageMapper.toResponse(image);
    }

    @Override
    public void deleteImage(String id)   {
        Image imageDelete = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        imageRepository.delete(imageDelete);
    }

    @Override
    public void deleteImageExpired() {
        var lstImage = imageRepository.findAll();
        for (Image image : lstImage) {
            if ((LocalDateTime.now().getMonthValue() - image.getCreatedAt().getMonthValue()) >= 3 ){
                imageRepository.delete(image);
            }
        }
    }

    @Override
    public ImageResponse updateImage(String id, ImageRequest imageRequest) {
        return null;
    }

    @Override
    public ImageResponse getAllImages()  {
        return null;
    }

    @Override
    public Image saveImageForOrder(Image image, String orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        }

        Image imageOrder = imageRepository.save(image);
        imageOrder.setOrderId(orderId);
        imageOrder.setUserName(username);
        imageOrder.setImgUrl(image.getImgUrl());
        imageOrder.setCount(imageOrder.getCount() + 1);

        return imageOrder;
    }

    @Override
    public ImageResponse downloadImage(String id) {
        Image imgDownload = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        log.info("Image count: {}", imgDownload.getCount());
        imgDownload.setCount(imgDownload.getCount() + 1);
        downloadImage(imgDownload.getImgUrl(), "C:\\Users\\ADMIN\\Downloads\\order_" + id + ".jpg");
        imageRepository.save(imgDownload);
        log.info("Image count: {}", imgDownload.getCount());
        return imageMapper.toResponse(imgDownload);
    }

    private void downloadImage(String imageUrl, String destinationPath) {
        try {
            if (imageUrl.startsWith("http")) {
                // Tải từ URL HTTP
                RestTemplate restTemplate = new RestTemplate();
                byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
                Files.write(Paths.get(destinationPath), imageBytes);
                log.info("Downloaded image to {}", destinationPath);
            } else {
                // Tải từ đường dẫn cục bộ
                Path sourcePath = Paths.get(imageUrl);
                Files.copy(sourcePath, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
                log.info("Copied image to {}", destinationPath);
            }
        } catch (IOException e) {
            log.error("Failed to download image from {}: {}", imageUrl, e.getMessage());
        }
    }


}
