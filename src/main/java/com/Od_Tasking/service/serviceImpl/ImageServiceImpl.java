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
        log.info("Image url is {}", imageOrder.getImgUrl());

        return imageOrder;
    }
}
