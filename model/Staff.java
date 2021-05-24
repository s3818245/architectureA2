package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int staff_id;

    @Column
        private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Order> orderList;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<ReceivingNote> receivingNotes;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<SaleInvoice> saleInvoices;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<DeliveryNote> deliveryNotes;

    public Staff(int staff_id, String name, String address, String phone, String email) {
        this.staff_id = staff_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.orderList = null;
        this.receivingNotes = null;
        this.saleInvoices = null;
        this.deliveryNotes = null;
    }

    public Staff(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.orderList = null;
        this.receivingNotes = null;
        this.saleInvoices = null;
        this.deliveryNotes = null;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staff_id=" + staff_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Staff() {
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int id) {
        this.staff_id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

