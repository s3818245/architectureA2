package com.example.sadi_assignment2_s3819293.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent a Delivery Note of a Sale Invoice
 */


@Entity
@Table(name = "deliverynote")
public class DeliveryNote {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int delivery_note_id;

    @Column
    private Date date;

    @OneToOne
    private Staff staff;

    @OneToMany(mappedBy = "deliveryNote")
    @Fetch(FetchMode.SUBSELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DeliveryDetail> deliveryDetails;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn //this table contain delivery note
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SaleInvoice saleInvoice;

    //constructors
    public DeliveryNote() {
    }

    public DeliveryNote(Date date, Staff staff, SaleInvoice saleInvoice) {
        this.date = date;
        this.staff = staff;
        this.deliveryDetails = null;
        this.saleInvoice = saleInvoice;
    }

    //getter and setter
    public int getDelivery_note_id() {
        return delivery_note_id;
    }

    public void setDelivery_note_id(int id) {
        this.delivery_note_id = id;
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

    public List<DeliveryDetail> getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(List<DeliveryDetail> deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public SaleInvoice getSaleInvoice() {
        return saleInvoice;
    }

    public void setSaleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoice = saleInvoice;
    }

    @Override
    public String toString() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        return "DeliveryNote{" +
                "delivery_note_id=" + delivery_note_id +
                ", date=" + dateFormat.format(date) +
                ", staff=" + staff.toString() +
                ", deliveryDetails=" + deliveryDetails.toString() +
                ", saleInvoice=" + saleInvoice.toString() +
                '}';
    }
}
