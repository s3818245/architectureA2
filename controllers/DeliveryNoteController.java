package com.quynhanh.architecturea2.controllers;

import com.quynhanh.architecturea2.model.DeliveryNote;
import com.quynhanh.architecturea2.service.DeliveryNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
public class DeliveryNoteController {
    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.GET)
    public List<DeliveryNote> getAllDeliveryNote() {
        return deliveryNoteService.getAllDeliveryNote();
    }

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.POST)
    public int addDeliveryNote(@RequestBody DeliveryNote deliveryNote) {
        return this.deliveryNoteService.addDeliveryDetails(this.deliveryNoteService.addDeliveryNote(deliveryNote));
    }

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.DELETE)
    public String deleteDeliveryNote(@RequestBody DeliveryNote deliveryNote) {
        return this.deliveryNoteService.deleteDeliveryNote(deliveryNote);
    }

    @RequestMapping(path = "/deliveryNotes", method = RequestMethod.PUT)
    public DeliveryNote updateDeliveryNote(@RequestBody DeliveryNote deliveryNote) {
        return this.deliveryNoteService.updateDeliveryDetails(this.deliveryNoteService.updateDeliveryNote(deliveryNote));
    }

    @RequestMapping(path = "/deliveryNotes/{id:[\\d]+}", method = RequestMethod.GET)
    public DeliveryNote getADeliveryNote(@PathVariable int id) {
        return this.deliveryNoteService.getOneDeliveryNote(id);
    }

    @RequestMapping(path = "/deliveryNotes/filter", method = RequestMethod.GET)
    public List<DeliveryNote> getNoteByDate(@RequestParam(required = false) String date, @RequestParam(required = false) String start, @RequestParam(required = false) String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date != null && start == null && end == null) {
            String startDate = date + " 00:00:00";
            String endDate = date + " 23:59:59";

            try {
                Date atStart = format.parse(startDate);
                Date atEnd = format.parse(endDate);
                return this.deliveryNoteService.getNoteByDate(atStart, atEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        else if(start != null && end != null) {
            start += " 00:00:00";
            end += " 23:59:59";

            try {
                Date atStart = format.parse(start);
                Date atEnd = format.parse(end);
                return this.deliveryNoteService.getNoteByDate(atStart, atEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            return null;
        }
    }

    @RequestMapping("/deliverNotes/*")
    public String pageNotFound() {
        return "Page not found";
    }
}

