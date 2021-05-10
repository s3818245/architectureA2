package com.example.sadi_assignment2_s3819293.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class SaleInvoice {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;

    @OneToOne
    private Staff staff;

    @OneToOne
    private Customer customer;

    @OneToMany(mappedBy = "salesInvoice")
    private List<SaleDetails> saleDetails;
}
