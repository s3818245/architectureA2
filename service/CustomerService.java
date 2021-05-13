package com.example.sadi_assignment2_s3819293.service;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import com.example.sadi_assignment2_s3819293.model.Customer;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Customer> getAllCustomer() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        return criteria.list();
    }

    public Customer getCustomerById(int id) {
        return sessionFactory.getCurrentSession().get(Customer.class, id);
    }

    public int addCustomer(Customer customer) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(customer);
        return customer.getCustomer_id();
    }

    public String deleteCustomer(Customer customer) {
        this.sessionFactory.getCurrentSession().delete(customer);
        return "Customer with id: " + customer.getCustomer_id() + " had been successfully deleted";
    }

    public Customer updateCustomer(Customer customer) {
        this.sessionFactory.getCurrentSession().update(customer);
        return customer;
    }

    public List<Customer> getCustomerByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        return criteria.list();
    }

    public List<Customer> getCustomerByAddress(String address) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("address", address, MatchMode.ANYWHERE));
        return criteria.list();
    }

    public List<Customer> getCustomerByPhone(String phone) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("phone", phone, MatchMode.ANYWHERE));
        return criteria.list();
    }
}
