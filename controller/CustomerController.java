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

    @RequestMapping(path = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @RequestMapping(path = "/customers/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @RequestMapping(path = "/customers", method = RequestMethod.POST)
    public int addCustomer(@RequestBody Customer customer) {
        this.customerService.addCustomer(customer);
        return customer.getCustomer_id();
    }

    @RequestMapping(path = "/customers/{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable int id) {
        Customer updateCustomer = customerService.getCustomerById(id);

        if (updateCustomer != null) {
            this.customerService.updateCustomer(updateCustomer);
        }

        return customer;
    }

    @RequestMapping(path = "/customers", method = RequestMethod.DELETE)
    public String deleteCustomer(@RequestBody Customer customer) {
        return this.customerService.deleteCustomer(customer);
    }

    @RequestMapping(path = "/customers", params = "name", method = RequestMethod.GET)
    public List<Customer> findCustomerByName(@RequestParam(value = "name") String name) {
        return this.customerService.getCustomerByName(name);
    }

    @RequestMapping(path = "/customers", params = "address", method = RequestMethod.GET)
    public List<Customer> findCustomerByAddress(@RequestParam(value = "address") String address) {
        return customerService.getCustomerByAddress(address);
    }

    @RequestMapping(path = "/customers", params = "phone", method = RequestMethod.GET)
    public List<Customer> findCustomerByPhone(@RequestParam(value = "phone") String phone) {
        return customerService.getCustomerByPhone(phone);
    }

}
