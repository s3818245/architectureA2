package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent a Customer of the company
 */

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String fax;

    @Column
    private String email;

    @Column
    private String contactPerson;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<SaleInvoice> saleInvoices;

    //constructors
    public Customer() {
    }

    public Customer(int customer_id, String name, String address, String phone, String fax, String email, String contactPerson) {
        this.customer_id = customer_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.contactPerson = contactPerson;
    }

    public Customer(String name, String address, String phone, String fax, String email, String contactPerson) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.contactPerson = contactPerson;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", contactPerson='" + contactPerson + '\'';
    }

    //getter and setter
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int id) {
        this.customer_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}

