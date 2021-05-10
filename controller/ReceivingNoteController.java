package com.example.sadi_assignment2_s3819293.controller;

import com.example.sadi_assignment2_s3819293.service.ReceivingNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceivingNoteController {
    @Autowired
    private ReceivingNoteService receivingNoteService;
}
