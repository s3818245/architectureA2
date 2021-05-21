package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int order_detail_id;

    @Column
    private int quantity;

    @Column
    private double price;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(int order_detail_id, int quantity, double price, Product product, Order order) {
        this.order_detail_id = order_detail_id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.order = order;
    }

    public OrderDetail(int quantity, double price, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    public OrderDetail(int order_detail_id, int quantity, double price, Product product) {
        this.order_detail_id = order_detail_id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.order = null;
    }

    public int getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(int id) {
        this.order_detail_id = id;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "order_detail_id=" + order_detail_id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product=" + product.toString() +
                '}';
    }
}

