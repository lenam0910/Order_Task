package com.Od_Tasking.entity.Orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data

@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String name;
    private String description;

    @JsonProperty("order_details")
    private List<OrderDetail> orderDetails;

    @LastModifiedDate
    @Field("created_at")
    private LocalDateTime createdAt ;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Field("created_by")
    private String createdBy;

    @LastModifiedBy
    @Field("updated_by")
    private String updatedBy;

    @JsonProperty("statuses")
    private List<Status> status = new ArrayList<>();

    @Field("currentStatus")
    private Status currentStatus;

    @Field("is_deleted")
    private boolean isDeleted = false;

    @Field("image_url")
    private String imageUrl;

}

