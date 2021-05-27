package com.example.sadi_assignment2_s3819293.controllers;

import com.example.sadi_assignment2_s3819293.model.ReceivingNote;
import com.example.sadi_assignment2_s3819293.service.ReceivingNoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 10, 2021
 *
 * This class represent a Controller for Receiving Note in a REST API, it contains all the paths for each operations in the Service
 */

@RestController
public class ReceivingNoteController {
    @Autowired
    private ReceivingNoteService receivingNoteService;

    @RequestMapping(path = "/receivingNotes", method = RequestMethod.GET )
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

    @RequestMapping(path = "receivingNotes/{id:[\\d]+}", method = RequestMethod.GET)
    public ReceivingNote getAReceivingNote(@PathVariable int id){
        return this.receivingNoteService.getOneReceivingNote(id);
    }

    //path for filtering by date or from start to end date
    @RequestMapping(path = "receivingNotes/filter", method = RequestMethod.GET)
    public List<ReceivingNote> getNoteByDate(@RequestParam(required = false) String date, @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date != null && start==null && end == null){
            //get notes in a date
            String startDate = date+" 00:00:00";
            String endDate = date+" 23:59:59";

            try {
                Date morning = format.parse(startDate);
                Date night = format.parse(endDate);
                return this.receivingNoteService.getNoteByDate(morning, night);
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

    @RequestMapping("receivingNotes/*")
    public String pageNotFound(){
        return "Page not found";
    }
}
