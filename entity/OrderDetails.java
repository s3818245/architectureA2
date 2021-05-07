package com.quynhanh.architecturea2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetails {
    @Column
    @Id
    private int id;

    @Column
    @ManyToOne
    private Product product;

    @Column
    @ManyToOne
    private Order order;
}
