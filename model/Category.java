package com.quynhanh.architecturea2.model;

import javax.persistence.*;

import java.util.List;

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

    public Category(int id, String name) {
        this.category_id = id;
        this.name = name;
    }

    public Category(int category_id) {
        this.category_id = category_id;
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
}

