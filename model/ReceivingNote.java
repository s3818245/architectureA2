package com.example.sadi_assignment2_s3819293.model;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ReceivingNote {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int receiving_note_id;

    @Column
    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Staff staff;

    @OneToMany(mappedBy = "receivingNote")
    private List<ReceivingDetail> receivingDetails;

    @OneToOne(mappedBy = "deliveryNote", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    public ReceivingNote() {
    }

    public ReceivingNote(int receiving_note_id, Date date, Staff staff, List<ReceivingDetail> receivingDetails, Order order) {
        this.receiving_note_id = receiving_note_id;
        this.date = date;
        this.staff = staff;
        this.receivingDetails = receivingDetails;
        this.order = order;
    }

    public int getReceiving_note_id() {
        return receiving_note_id;
    }

    public void setReceiving_note_id(int receiving_note_id) {
        this.receiving_note_id = receiving_note_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<ReceivingDetail> getReceivingDetails() {
        return receivingDetails;
    }

    public void setReceivingDetails(List<ReceivingDetail> receivingDetails) {
        this.receivingDetails = receivingDetails;
    }
}

