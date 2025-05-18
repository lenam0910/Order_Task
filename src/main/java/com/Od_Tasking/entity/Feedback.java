package com.Od_Tasking.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "feedbacks")
public class Feedback {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    String id;

    @Field("user_name")
    String userName;

    @Field("order_id")
    String orderId;

    @Field("feedback_text")
    String feedbackText;

    @Field("rating")
    int rating;


    @Field("is_deleted")
    boolean isDeleted = false;

    @LastModifiedDate
    @Field("created_at")
    LocalDateTime createdAt;


}
