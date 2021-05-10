package com.example.sadi_assignment2_s3819293.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> product;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(int id) {
        this.id = id;
    }

    public Category() {
    }

    //getters and setters
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
}
