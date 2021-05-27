package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent an Order made by the company to get product(s)
 */


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int order_id;

    @Column
    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT) //prevent order duplicates
    private List<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "order", cascade = CascadeType.MERGE)
    @JsonIgnore
    private ReceivingNote receivingNote;

    //constructors
    public Order(int order_id, Date date, Staff staff, Provider provider, List<OrderDetail> orderDetails, ReceivingNote receivingNote) {
        this.order_id = order_id;
        this.date = date;
        this.staff = staff;
        this.provider = provider;
        this.orderDetails = orderDetails;
        this.receivingNote = receivingNote;
    }

    public Order(int order_id, Date date, Staff staff, Provider provider, List<OrderDetail> orderDetails) {
        this.order_id = order_id;
        this.date = date;
        this.staff = staff;
        this.provider = provider;
        this.orderDetails = orderDetails;
        this.receivingNote = null;
    }

    public Order(Date date, Staff staff, Provider provider, List<OrderDetail> orderDetails) {
        this.date = date;
        this.staff = staff;
        this.provider = provider;
        this.orderDetails = orderDetails;
        this.receivingNote = null;
    }

    public Order() {
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return "Order{" +
                "order_id=" + order_id +
                ", date=" + dateFormat.format(date) +
                ", staff=" + staff.toString() +
                ", provider=" + provider.toString() +
                ", orderDetails=" + orderDetails.toString() +
                '}';
    }

    //getter and setter
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int id) {
        this.order_id = id;
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ReceivingNote getReceivingNote() {
        return receivingNote;
    }

    public void setReceivingNote(ReceivingNote receivingNote) {
        this.receivingNote = receivingNote;
    }

}
