package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.sadi_assignment2_s3819293.model.Order;
import com.example.sadi_assignment2_s3819293.service.OrderService;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/orders", method = RequestMethod.GET)
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }
}
