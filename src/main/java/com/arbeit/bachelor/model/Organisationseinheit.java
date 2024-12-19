package com.arbeit.bachelor.model;

import jakarta.persistence.*;

@Entity
public class Organisationseinheit {
    @Id
    private String name;

    @ManyToOne
    @JoinColumn(name = "behoerde")
    private Behoerde behoerde;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Behoerde getBehoerde() {
        return behoerde;
    }

    public void setBehoerde(Behoerde behoerde) {
        this.behoerde = behoerde;
    }
}

