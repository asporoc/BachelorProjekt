package com.arbeit.bachelor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Behoerde {
    @Id
    private String name;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
