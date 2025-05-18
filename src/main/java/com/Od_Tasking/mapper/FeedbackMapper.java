package com.Od_Tasking.mapper;

import com.Od_Tasking.dto.Request.FeedbackRequest;
import com.Od_Tasking.dto.Respone.FeedbackRespone;
import com.Od_Tasking.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackRespone toRespone(Feedback feedback);

    Feedback toFeedback(FeedbackRequest feedbackRequest);

}
