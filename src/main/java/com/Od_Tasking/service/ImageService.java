package com.Od_Tasking.service;


import com.Od_Tasking.dto.Request.ImageRequest;
import com.Od_Tasking.dto.Respone.ImageResponse;
import com.Od_Tasking.entity.Image;

public interface ImageService {

    ImageResponse getImageById(String id) ;
    ImageResponse saveImage(ImageRequest imageRequest,String orderId) ;
    void deleteImage(String id) ;
    void deleteImageExpired();
    ImageResponse updateImage(String id, ImageRequest imageRequest) ;
    ImageResponse getAllImages() ;
    Image saveImageForOrder(Image image, String orderId) ;
}
