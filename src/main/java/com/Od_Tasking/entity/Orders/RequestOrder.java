package com.Od_Tasking.entity.Orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Data
public class RequestOrder {
    @JsonProperty("geometry")
    private Geometry geometry;
    @JsonProperty("properties")
    private Map<String, Object> properties;
}
