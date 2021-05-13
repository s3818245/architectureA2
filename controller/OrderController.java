package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/orders", method = RequestMethod.POST)
    public int addOrder(@RequestBody Order order){
        return this.orderService.addOrder(order);
    }

    @RequestMapping(path = "/orders", method = RequestMethod.PUT)
    public Order updateOrder(@RequestBody Order order){
        return this.orderService.updateOrder(order);
    }

    @RequestMapping(path = "/orders", method = RequestMethod.DELETE)
    public String deleteOrder(@RequestBody Order order){
        return this.orderService.deleteOrder(order);
    }

    @RequestMapping(path = "/orders/{id}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable("id") int id){
        return this.orderService.getAnOrder(id);
    }
}
