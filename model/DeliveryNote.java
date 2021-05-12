package com.quynhanh.architecturea2.model;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class DeliveryNote {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;

    @OneToOne
    private Staff staff;

    @OneToMany(mappedBy = "deliveryDetail")
    private List<DeliveryDetail> deliveryDetails;

    public DeliveryNote() {
    }

    public DeliveryNote(int id, Date date, Staff staff, List<DeliveryDetail> deliveryDetails) {
        this.id = id;
        this.date = date;
        this.staff = staff;
        this.deliveryDetails = deliveryDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<DeliveryDetail> getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(List<DeliveryDetail> deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }
}
