package com.Od_Tasking.dto.Request;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class FeedbackRequest {

    String userId;
    String orderId;
    String feedbackText;
    int rating;
    String image;

}
