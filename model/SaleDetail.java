package com.example.sadi_assignment2_s3819293.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class SaleDetail {

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
    @JsonIgnore
    private SalesInvoice salesInvoice;

}
