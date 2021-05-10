package com.example.sadi_assignment2_s3819293.model;

import javax.persistence.*;

@Entity
public class OrderDetails {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private int quantity;

    @Column
    private double price;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;
}
