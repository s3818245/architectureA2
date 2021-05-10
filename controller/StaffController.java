package com.example.sadi_assignment2_s3819293.controller;

import com.example.sadi_assignment2_s3819293.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;
}
