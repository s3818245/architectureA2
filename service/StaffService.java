package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.Staff;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
