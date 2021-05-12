package com.quynhanh.architecturea2.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int order_id;

    @Column
    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DeliveryNote deliveryNote;

    public Order(int order_id, Date date, Staff staff, Provider provider, List<OrderDetail> orderDetails) {
        this.order_id = order_id;
        this.date = date;
        this.staff = staff;
        this.provider = provider;
        this.orderDetails = orderDetails;
    }

    public Order() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int id) {
        this.order_id = id;
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
