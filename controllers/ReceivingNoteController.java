package com.quynhanh.architecturea2.controllers;

import com.quynhanh.architecturea2.model.ReceivingNote;
import com.quynhanh.architecturea2.service.ReceivingNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReceivingNoteController {
    @Autowired
    private ReceivingNoteService receivingNoteService;

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.GET)
    public List<ReceivingNote> getAllReceivingNote() {
        return receivingNoteService.getAllReceivingNote();
    }
}
