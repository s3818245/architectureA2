package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class DeliveryDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int delivery_detail_id;

    @ManyToOne
    private Product product;

    @Column
    private int quantity;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DeliveryNote deliveryNote;

    public DeliveryDetail() {
    }

    public DeliveryDetail(int delivery_detail_id, Product product, int quantity, DeliveryNote deliveryNote) {
        this.delivery_detail_id = delivery_detail_id;
        this.product = product;
        this.quantity = quantity;
        this.deliveryNote = deliveryNote;
    }

    public int getDelivery_detail_id() {
        return delivery_detail_id;
    }

    public void setDelivery_detail_id(int id) {
        this.delivery_detail_id = id;
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

    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }
}
