package com.example.sadi_assignment2_s3819293.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class DeliveryNote {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;

    @OneToOne
    private Staff staff;

    @OneToMany(mappedBy = "deliveryDetail")
    private List<DeliveryDetail> deliveryDetails;
}
