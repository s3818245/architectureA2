package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent a Receiving Note of an Order
 */


@Entity
public class ReceivingNote {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int receiving_note_id;

    @Column
    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Staff staff;

    @OneToMany(mappedBy = "receivingNote")
    @Fetch(FetchMode.SUBSELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ReceivingDetail> receivingDetails;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn //this table contain receiving not
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    //constructors
    public ReceivingNote() {
    }

    public ReceivingNote(Date date, Staff staff, Order order) {
        this.date = date;
        this.staff = staff;
        this.receivingDetails = null;
        this.order = order;
    }

    //getter and setter
    public int getReceiving_note_id() {
        return receiving_note_id;
    }

    public void setReceiving_note_id(int receiving_note_id) {
        this.receiving_note_id = receiving_note_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<ReceivingDetail> getReceivingDetails() {
        return receivingDetails;
    }

    public void setReceivingDetails(List<ReceivingDetail> receivingDetails) {
        this.receivingDetails = receivingDetails;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return "ReceivingNote{" +
                "receiving_note_id=" + receiving_note_id +
                ", date=" + dateFormat.format(date) +
                ", staff=" + staff +
                ", receivingDetails=" + receivingDetails.toString() +
                '}';
    }
}

