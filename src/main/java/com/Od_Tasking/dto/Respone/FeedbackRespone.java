package com.Od_Tasking.dto.Respone;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FeedbackRespone {

    private String id;
    private String feedbackText;
    private String rating;
    private String imageUrl;
    private String createdAt;
    private String userName;
    String imgFeedback;

}
