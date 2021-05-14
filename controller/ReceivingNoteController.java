package com.quynhanh.architecturea2.controllers;

import com.quynhanh.architecturea2.model.ReceivingNote;
import com.quynhanh.architecturea2.service.ReceivingNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReceivingNoteController {
    @Autowired
    private ReceivingNoteService receivingNoteService;

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.GET)
    public List<ReceivingNote> getAllReceivingNote() {
        return receivingNoteService.getAllReceivingNote();
    }

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.POST)
    public int addReceivingNote(@RequestBody ReceivingNote receivingNote){
        return this.receivingNoteService.addReceivingDetails(this.receivingNoteService.addReceivingNote(receivingNote));
    }

//    @RequestMapping(path = "/receivingNotes", method = RequestMethod.PUT)
//    public ReceivingNote updateReceivingNotes(){
//
//    }

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.DELETE)
    public String deleteReceivingNote(@RequestBody ReceivingNote receivingNote){
        return this.receivingNoteService.deleteReceivingNote(receivingNote);
    }

    @RequestMapping(path = "receivingNotes/{id}", method = RequestMethod.GET)
    public ReceivingNote getAReceivingNote(@PathVariable int id){
        return this.receivingNoteService.getOneReceivingNote(id);
    }
}
