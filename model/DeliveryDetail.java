package com.quynhanh.architecturea2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class DeliveryDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Product product;

    @Column
    private int quantity;

    @ManyToOne
    @JsonIgnore
    private DeliveryDetail deliveryDetail;

    public DeliveryDetail() {
    }

    public DeliveryDetail(int id, Product product, int quantity, DeliveryDetail deliveryDetail) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.deliveryDetail = deliveryDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DeliveryDetail getDeliveryDetail() {
        return deliveryDetail;
    }

    public void setDeliveryDetail(DeliveryDetail deliveryDetail) {
        this.deliveryDetail = deliveryDetail;
    }
}
