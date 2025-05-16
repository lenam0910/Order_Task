package com.Od_Tasking.controller;

import com.Od_Tasking.entity.Orders.Order;
import com.Od_Tasking.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id) throws IOException, InterruptedException {
        return orderService.getOrderById(id);
    }
    @PostMapping("/save/{id}")
    public String saveOrder(@PathVariable Long id) throws IOException, InterruptedException {
        return orderService.saveOrder(id);
    }
    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id) throws IOException, InterruptedException {
        return orderService.processOrder(id);
    }
    @GetMapping()
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }
//    @GetMapping("/cookie")
//    public String getCookie(){
//        return orderService.getCookie();
//    }
}
