package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.sadi_assignment2_s3819293.model.Customer;
import com.example.sadi_assignment2_s3819293.service.CustomerService;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(path = "/getAllCustomers", method = RequestMethod.GET)
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @RequestMapping(path = "/getCustomer/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @RequestMapping(path = "/addCustomer", method = RequestMethod.POST)
    public int addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return customer.getCustomer_id();
    }

    @RequestMapping(path = "/updateCustomer/{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable int id) {
        Customer updateCustomer = customerService.getCustomerById(id);

        if (updateCustomer != null) {
            customerService.updateCustomer(updateCustomer);
        }

        return customer;
    }

    @RequestMapping(path = "/deleteCustomer", method = RequestMethod.DELETE)
    public void deleteCustomer(@RequestBody Customer customer) {
        customerService.deleteCustomer(customer);
    }

    @RequestMapping(path = "/findCustomerByName/{name}", method = RequestMethod.GET)
    public List<Customer> findCustomerByName(@PathVariable String name) {
        return customerService.getCustomerByName(name);
    }

    @RequestMapping(path = "/findCustomerByAddress/{address}", method = RequestMethod.GET)
    public List<Customer> findCustomerByAddress(@PathVariable String address) {
        return customerService.getCustomerByAddress(address);
    }

    @RequestMapping(path = "/findCustomerByPhone/{phone}", method = RequestMethod.GET)
    public List<Customer> findCustomerByPhone(@PathVariable String phone) {
        return customerService.getCustomerByPhone(phone);
    }

}
