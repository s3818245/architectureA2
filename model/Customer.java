package com.example.sadi_assignment2_s3819293.model;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column
    private int id;

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

    public Customer() {
    }

    public Customer(int id, String name, String address, String phone, String fax, String email, String contactPerson) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.contactPerson = contactPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
