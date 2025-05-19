package com.Od_Tasking.dto.Respone;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ImageResponse {
    String id;

    String imgUrl;

    LocalDateTime createdAt;

    String orderId;

    int count;

    String userName;


}
