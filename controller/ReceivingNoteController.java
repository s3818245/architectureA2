package com.example.sadi_assignment2_s3819293.controller;

import com.example.sadi_assignment2_s3819293.model.ReceivingNote;
import com.example.sadi_assignment2_s3819293.service.ReceivingNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.PUT)
    public ReceivingNote updateReceivingNotes(@RequestBody ReceivingNote receivingNote){
        return this.receivingNoteService.updateReceivingDetails(this.receivingNoteService.updateReceivingNote(receivingNote));
    }

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.DELETE)
    public String deleteReceivingNote(@RequestBody ReceivingNote receivingNote){
        return this.receivingNoteService.deleteReceivingNote(receivingNote);
    }

    @RequestMapping(path = "/receivingNotes/{id}", method = RequestMethod.GET)
    public ReceivingNote getAReceivingNote(@PathVariable int id){
        return this.receivingNoteService.getOneReceivingNote(id);
    }

    @RequestMapping(path = "/receivingNotes/filter", method = RequestMethod.GET)
    public List<ReceivingNote> getNoteByDate(@RequestParam(required = false) String date, @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date != null && start==null && end == null){
            //get notes in a date
            String startDate = date+" 00:00:00";
            String endDate = date+" 23:59:59";

            try {
                Date atStart = format.parse(startDate);
                Date atEnd = format.parse(endDate);
                return this.receivingNoteService.getNoteByDate(atStart, atEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }else if(start!=null && end!=null){
            start+=" 00:00:00";
            //get note between dates
            end+=" 23:59:59";//make sure to get notes during the end date
            try {
                Date startDate = format.parse(start);
                Date endDate = format.parse(end);
                return this.receivingNoteService.getNoteByDate(startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }
}
