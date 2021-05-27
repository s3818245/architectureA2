package com.example.sadi_assignment2_s3819293.controllers;

import com.example.sadi_assignment2_s3819293.model.Order;
import com.example.sadi_assignment2_s3819293.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 10, 2021
 *
 * This class represent a Controller for Order in a REST API, it contains all the paths for each operations in the Service
 */

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

    @RequestMapping(path = "/orders/{id:[\\d]+}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable("id") int id){
        return this.orderService.getAnOrder(id);
    }

    @RequestMapping("orders/*")
    public String pageNotFound(){
        return "Page not found";
    }

    @RequestMapping("*")
    public String generalPageNotFound(){
        return "Page not found";
    }
}
