package com.Od_Tasking.entity.Orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class OrderDetail {
    private Long id;
    @JsonProperty("provider")
    private Provider provider;
    @JsonProperty("request_order")
    private RequestOrder requestOrder;
}
