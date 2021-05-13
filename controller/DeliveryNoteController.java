package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/deliveryNotes/{id}", method = RequestMethod.GET)
    public DeliveryNote getDeliveryNote(@PathVariable int id) {
        return this.deliveryNoteService.getOneDeliveryNote(id);
    }

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.POST)
    public int addDeliveryNote(DeliveryNote deliveryNote) {
        return this.deliveryNoteService.addDeliveryNote(deliveryNote);
    }

    @RequestMapping(path = "/deliveryNotes/{id}", method = RequestMethod.DELETE)
    public String deleteDeliveryNote(@PathVariable int id) {
        return this.deliveryNoteService.deleteDeliveryNote(id);
    }

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.PUT)
    public DeliveryNote updateDeliveryNote(@RequestBody DeliveryNote deliveryNote) {
        return this.deliveryNoteService.updateDeliveryNote(deliveryNote);
    }
}
