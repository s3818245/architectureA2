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

    @RequestMapping(path = "/staffs/{id}", method = RequestMethod.PUT)
    public Staff updateStaff(@PathVariable int id, @RequestBody Staff staff){
        Staff oldStaff = this.staffService.getAStaff(id);

        if (oldStaff != null){
            if (staff.getAddress() == null){
                staff.setAddress(oldStaff.getAddress());
            }
            if(staff.getEmail() == null){
                staff.setEmail(oldStaff.getEmail());
            }
            if(staff.getName() == null){
                staff.setName(oldStaff.getName());
            }
            if(staff.getAddress() == null){
                staff.setAddress(oldStaff.getAddress());
            }
            if(staff.getPhone() == null){
                staff.setPhone(oldStaff.getPhone());
            }
        }else{

        }
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
