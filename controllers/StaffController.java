package com.example.sadi_assignment2_s3819293.controllers;

import com.example.sadi_assignment2_s3819293.model.Staff;
import com.example.sadi_assignment2_s3819293.service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 10, 2021
 *
 * This class represent a Controller for Staff in a REST API, it contains all the paths for each operations in the Service
 */

@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;

    @RequestMapping(path = "/staffs", method = RequestMethod.GET)
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }


    @RequestMapping(path = "/staffs", method = RequestMethod.POST)
    public int addStaff(@RequestBody Staff staff){
        return staffService.addStaff(staff);
    }

    @RequestMapping(path = "/staffs", method = RequestMethod.PUT)
    public Staff updateStaff(@RequestBody Staff staff){
        return this.staffService.updateStaff(staff);
    }

    @RequestMapping(path = "/staffs", method = RequestMethod.DELETE)
    public String deleteStaff(@RequestBody Staff staff){
        return this.staffService.deleteStaff(staff);
    }

    @RequestMapping(path = "/staffs/{id}", method = RequestMethod.GET)
    public Staff getAStaff(@PathVariable int id){
        return staffService.getAStaff(id);
    }

    @RequestMapping("staffs/*")
    public String pageNotFound(){
        return "page not found";
    }
}
