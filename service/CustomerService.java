package com.quynhanh.architecturea2.service;
import com.quynhanh.architecturea2.model.Customer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        return this.sessionFactory.getCurrentSession().get(Customer.class, id);
    }

    public int addCustomer(Customer customer) {
        this.sessionFactory.getCurrentSession().save(customer);
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
