package com.Od_Tasking.dto.Respone;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FeedbackRespone {

    private String id;
    private String orderId;
    private String feedbackText;
    private int rating;
    private String createdAt;
    private String userName;
}
