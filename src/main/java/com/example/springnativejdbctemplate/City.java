package com.example.springnativejdbctemplate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class City {
    @Id
    private String id;

    private String name;

    City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    City() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
