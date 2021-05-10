package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.sadi_assignment2_s3819293.model.Staff;
import com.example.sadi_assignment2_s3819293.service.StaffService;

@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;

    @RequestMapping(path = "/staffs", method = RequestMethod.GET)
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }
}