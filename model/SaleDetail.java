package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent a Sale Detail of an Invoice
 */


@Entity
@Table(name = "saledetail")
public class SaleDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int sale_detail_id;

    @Column
    private int quantity;

    @Column
    private double price;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column
    private double totalValue = 0;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SaleInvoice salesInvoice;

    //constructors
    public SaleDetail() {
    }

    public SaleDetail(int sale_detail_id, int quantity, double price, Product product, SaleInvoice salesInvoice) {
        this.sale_detail_id = sale_detail_id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.salesInvoice = salesInvoice;
    }

    public SaleDetail(int sale_detail_id, int quantity, double price, Product product) {
        this.sale_detail_id = sale_detail_id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.salesInvoice = null;
    }

    public SaleDetail(int quantity, double price, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    //getter and setter
    public int getSale_detail_id() {
        return sale_detail_id;
    }

    public void setSale_detail_id(int id) {
        this.sale_detail_id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SaleInvoice getSalesInvoice() {
        return salesInvoice;
    }

    public void setSalesInvoice(SaleInvoice salesInvoice) {
        this.salesInvoice = salesInvoice;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "SaleDetail{" +
                "sale_detail_id=" + sale_detail_id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product=" + product.toString() +
                ", totalValue=" + totalValue +
                '}';
    }
}

