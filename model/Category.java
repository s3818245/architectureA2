package com.example.sadi_assignment2_s3819293.model;

import javax.persistence.*;

import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 4, 2021
 *
 * This class represent a Category of a product
 */

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int category_id;

    @Column
    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> product;

    //constructor
    public Category(int id, String name) {
        this.category_id = id;
        this.name = name;
    }

    public Category(int category_id) {
        this.category_id = category_id;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    //getters and setters
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int id) {
        this.category_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                ", name='" + name + '\'' +
                '}';
    }
}

