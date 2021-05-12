package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.sadi_assignment2_s3819293.model.ReceivingNote;
import com.example.sadi_assignment2_s3819293.service.ReceivingNoteService;

@RestController
public class ReceivingNoteController {
    @Autowired
    private ReceivingNoteService receivingNoteService;

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.GET)
    public List<ReceivingNote> getAllReceivingNote() {
        return receivingNoteService.getAllReceivingNote();
    }

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.POST)
    public int addReceivingNote(ReceivingNote receivingNote) {
        return receivingNoteService.addReceivingNote(receivingNote);
    }
}
