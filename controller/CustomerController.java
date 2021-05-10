package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.sadi_assignment2_s3819293.service.CustomerService;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
}
