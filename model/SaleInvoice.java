package com.example.sadi_assignment2_s3819293.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
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
}
