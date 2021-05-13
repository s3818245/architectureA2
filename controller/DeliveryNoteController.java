package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.sadi_assignment2_s3819293.model.DeliveryNote;
import com.example.sadi_assignment2_s3819293.service.DeliveryNoteService;

@RestController
public class DeliveryNoteController {
    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.GET)
    public List<DeliveryNote> getAllDeliveryNote() {
        return deliveryNoteService.getAllDeliveryNote();
    }

    @RequestMapping(path = "/deliveryNote/{id}", method = RequestMethod.GET)
    public DeliveryNote getDeliveryNote(@PathVariable int id) {
        return deliveryNoteService.getOneDeliveryNote(id);
    }

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.POST)
    public int addDeliveryNote(DeliveryNote deliveryNote) {
        return deliveryNoteService.addDeliveryNote(deliveryNote);
    }
}
