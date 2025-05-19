package com.Od_Tasking.mapper;

import com.Od_Tasking.dto.Request.ImageRequest;
import com.Od_Tasking.dto.Respone.ImageResponse;
import com.Od_Tasking.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageRequest imageRequest);

    ImageResponse toResponse(Image image);
}
