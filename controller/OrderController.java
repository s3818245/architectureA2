package com.quynhanh.architecturea2.controllers;

import com.quynhanh.architecturea2.model.Order;
import com.quynhanh.architecturea2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(path = "/order/{id}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable("id") int id){
        return this.orderService.getAnOrder(id);
    }
}
