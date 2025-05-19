package com.Od_Tasking.dto.Request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ImageRequest {
    String imgUrl;
}
