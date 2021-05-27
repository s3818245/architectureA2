package com.example.sadi_assignment2_s3819293.controllers;

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

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 10, 2021
 *
 * This class represent a Controller for Inventory Note in a REST API, it contain all the path for each operations in the Service
 */

@RestController
public class InventoryNoteController {
    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(path = "/inventory", method = RequestMethod.GET)
    public List<String> getInventory(@RequestParam(required = false) String start, @RequestParam(required = false) String end) {
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

    @RequestMapping("/inventory/*")
    public String pageNotFound() {
        return "Page not found";
    }
}
