package com.Od_Tasking.service;


import com.Od_Tasking.dto.Request.ImageRequest;
import com.Od_Tasking.entity.Orders.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    String getOrderById(Long id) throws IOException, InterruptedException;
    String saveOrder(Long id) throws IOException, InterruptedException;
    List<Order> getAllOrder();
    String processOrder(Long id);
    String confirmOrder(Long id, ImageRequest imageRequest);
    List<Order> getOrderOfUser(String id);
//    String getCookie();
}
