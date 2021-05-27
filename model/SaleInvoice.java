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
 * This class represent a Sale Invoice made by a Customer buying from the company
 */


@Entity
@Table(name = "saleinvoice")
public class SaleInvoice {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int sale_invoice_id;

    @Column
    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "salesInvoice", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT) //prevent order duplicates
    private List<SaleDetail> saleDetails;

    @OneToOne(mappedBy = "saleInvoice", cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DeliveryNote deliveryNote;

    @Column
    private double totalPrice = 0;

    //constructors
    public SaleInvoice() {
    }

    public SaleInvoice(int sale_invoice_id, Date date, Staff staff, Customer customer, List<SaleDetail> saleDetails, DeliveryNote deliveryNote) {
        this.sale_invoice_id = sale_invoice_id;
        this.date = date;
        this.staff = staff;
        this.customer = customer;
        this.saleDetails = saleDetails;
        this.deliveryNote = deliveryNote;
    }

    public SaleInvoice(int sale_invoice_id, Date date, Staff staff, Customer customer, List<SaleDetail> saleDetails) {
        this.sale_invoice_id = sale_invoice_id;
        this.date = date;
        this.staff = staff;
        this.customer = customer;
        this.saleDetails = saleDetails;
        this.deliveryNote = null;
    }

    public SaleInvoice(Date date, Staff staff, Customer customer, List<SaleDetail> saleDetails) {
        this.date = date;
        this.staff = staff;
        this.customer = customer;
        this.saleDetails = saleDetails;
        this.deliveryNote = null;
    }

    //getter and setter
    public int getSale_invoice_id() {
        return sale_invoice_id;
    }

    public void setSale_invoice_id(int id) {
        this.sale_invoice_id = id;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return "SaleInvoice{" +
                "sale_invoice_id=" + sale_invoice_id +
                ", date=" + dateFormat.format(date) +
                ", staff=" + staff.toString() +
                ", customer=" + customer.toString() +
                ", saleDetails=" + saleDetails.toString() +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
