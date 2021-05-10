package com.example.sadi_assignment2_s3819293.controller;

import com.example.sadi_assignment2_s3819293.model.DeliveryNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sadi_assignment2_s3819293.service.DeliveryNoteService;

import java.util.List;

@RestController
public class DeliveryNoteController {
    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.GET)
    public List<DeliveryNote> getAllCustomer() {
        return deliveryNoteService.getAllDeliveryNote();
    }
}
