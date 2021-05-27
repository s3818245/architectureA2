package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.Staff;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 10, 2021
 *
 * This class represent a Staff Service, which contain all the CRUD operations
 */

@Service
@Transactional
public class StaffService {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Staff> getAllStaff() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Staff.class);
        return criteria.list();
    }

    public int addStaff(Staff staff){
        this.sessionFactory.getCurrentSession().save(staff);
        return staff.getStaff_id();
    }

    public String deleteStaff(Staff staff){
        this.sessionFactory.getCurrentSession().delete(staff);
        return "Staff with id "+ staff.getStaff_id() + " is deleted";
    }

    public Staff updateStaff(Staff staff){
        this.getSessionFactory().getCurrentSession().update(staff);
        return staff;
    }

    public Staff getAStaff(int staff_id){
        return this.sessionFactory.getCurrentSession().get(Staff.class, staff_id);
    }
}
