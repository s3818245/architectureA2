package com.quynhanh.architecturea2.controllers;

import com.quynhanh.architecturea2.model.DeliveryNote;
import com.quynhanh.architecturea2.service.DeliveryNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
