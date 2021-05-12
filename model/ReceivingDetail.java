package com.quynhanh.architecturea2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class ReceivingDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int receiving_detail_id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Product product;

    @Column
    private int quantity;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ReceivingNote receivingNote;

    public ReceivingDetail() {
    }

    public ReceivingDetail(int receiving_detail_id, Product product, int quantity, ReceivingNote receivingNote) {
        this.receiving_detail_id = receiving_detail_id;
        this.product = product;
        this.quantity = quantity;
        this.receivingNote = receivingNote;
    }

    public int getReceiving_detail_id() {
        return receiving_detail_id;
    }

    public void setReceiving_detail_id(int id) {
        this.receiving_detail_id = id;
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

    public ReceivingNote getReceivingNote() {
        return receivingNote;
    }

    public void setReceivingNote(ReceivingNote receivingNote) {
        this.receivingNote = receivingNote;
    }
}

