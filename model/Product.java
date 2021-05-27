package com.example.sadi_assignment2_s3819293.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent a Product being sold by the company
 */


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

    //constructors
    public Product(){}

    public Product(int id, String name, String model, String brand, String company, String description, Category category, double sellingPrice, List<OrderDetail> orderDetails) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.company = company;
        this.description = description;
        this.category = category;
        this.sellingPrice = sellingPrice;
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

    public Product(String name, String model, String brand, String company, String description, Category category, double sellingPrice) {
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.company = company;
        this.description = description;
        this.category = category;
        this.sellingPrice = sellingPrice;
    }

    //getter and setter
    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
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

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", company='" + company + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category.toString() +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
