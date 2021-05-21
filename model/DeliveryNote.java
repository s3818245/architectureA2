package com.example.sadi_assignment2_s3819293.model;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
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
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SaleInvoice saleInvoice;

    public DeliveryNote() {
    }

    public DeliveryNote(int delivery_note_id, Date date, Staff staff, List<DeliveryDetail> deliveryDetails, SaleInvoice saleInvoice) {
        this.delivery_note_id = delivery_note_id;
        this.date = date;
        this.staff = staff;
        this.deliveryDetails = deliveryDetails;
        this.saleInvoice = saleInvoice;
    }

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
}
