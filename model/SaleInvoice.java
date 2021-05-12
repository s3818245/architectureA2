package com.example.sadi_assignment2_s3819293.model;
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
    private Staff staff;

    @ManyToOne
    private Customer customer;


    @OneToMany(mappedBy = "salesInvoice")
    private List<SaleDetail> saleDetails;

    public SaleInvoice() {
    }

    public SaleInvoice(int sale_invoice_id, Date date, Staff staff, Customer customer, List<SaleDetail> saleDetails) {
        this.sale_invoice_id = sale_invoice_id;
        this.date = date;
        this.staff = staff;
        this.customer = customer;
        this.saleDetails = saleDetails;
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
}
