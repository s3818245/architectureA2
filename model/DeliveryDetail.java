package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class DeliveryDetail {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Product product;

    @Column
    private int quantity;

    @ManyToOne
    @JsonIgnore
    private DeliveryDetail deliveryDetail;
}
