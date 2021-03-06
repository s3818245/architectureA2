package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent a Provider of products to the company
 */


@Entity
@Table(name = "provider")
public class Provider {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int provider_id;

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

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orderList;

    //constructors
    public Provider(int provider_id, String name, String address, String phone, String fax, String email, String contactPerson) {
        this.provider_id = provider_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.contactPerson = contactPerson;
    }

    public Provider(String name, String address, String phone, String fax, String email, String contactPerson) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.contactPerson = contactPerson;
    }

    public Provider() {

    }

    //getter and setter
    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int id) {
        this.provider_id = id;
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

    @Override
    public String toString() {
        return "Provider{" +
                "provider_id=" + provider_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }
}

