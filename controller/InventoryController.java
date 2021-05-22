package com.example.sadi_assignment2_s3819293.controller;

import com.example.sadi_assignment2_s3819293.model.Product;
import com.example.sadi_assignment2_s3819293.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(path = "/inventory", method = RequestMethod.GET)
    public List<Integer> getInventory(@RequestParam(required = false) String start, @RequestParam(required = false) String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (start != null && end != null) {
            try {
                Date startDate = format.parse(start);
                Date endDate = format.parse(end);

                return this.inventoryService.getInventory(startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            return null;
        }
    }
}
