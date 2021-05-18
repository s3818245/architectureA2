package com.example.sadi_assignment2_s3819293.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String model;

    @Column
    private String brand;

    @Column
    private String company;

    @Column
    private String description;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="category_id")
    private Category category;

    @Column
    private double sellingPrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReceivingDetail> receivingDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<SaleDetail> saleDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DeliveryDetail> deliveryDetails;

    public Product() {
    }

    public Product(int id, String name, String model, String brand, String company, String description, Category category, double sellingPrice, List<OrderDetail> orderDetails, List<ReceivingDetail> receivingDetails, List<SaleDetail> saleDetails, List<DeliveryDetail> deliveryDetails) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.company = company;
        this.description = description;
        this.category = category;
        this.sellingPrice = sellingPrice;
        this.orderDetails = orderDetails;
        this.receivingDetails = receivingDetails;
        this.saleDetails = saleDetails;
        this.deliveryDetails = deliveryDetails;
    }

    public Product(int id, String name, String model, String brand, String company, String description, Category category, double sellingPrice) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.company = company;
        this.description = description;
        this.category = category;
        this.sellingPrice = sellingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<ReceivingDetail> getReceivingDetails() {
        return receivingDetails;
    }

    public void setReceivingDetails(List<ReceivingDetail> receivingDetails) {
        this.receivingDetails = receivingDetails;
    }

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public List<DeliveryDetail> getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(List<DeliveryDetail> deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }
}
