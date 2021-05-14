package com.quynhanh.architecturea2.controllers;

import com.quynhanh.architecturea2.model.Staff;
import com.quynhanh.architecturea2.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
