package com.arbeit.bachelor.model;


import jakarta.persistence.*;

@Entity
public class Bewirtschafter {
    @Id
    private String name;

    @ManyToOne
    @JoinColumn(name = "org")
    private Organisationseinheit organisationseinheit;

    @ManyToOne
    @JoinColumn(name = "behoerde")
    private Behoerde behoerde;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organisationseinheit getOrganisationseinheit() {
        return organisationseinheit;
    }

    public void setOrganisationseinheit(Organisationseinheit organisationseinheit) {
        this.organisationseinheit = organisationseinheit;
    }

    public Behoerde getBehoerde() {
        return behoerde;
    }

    public void setBehoerde(Behoerde behoerde) {
        this.behoerde = behoerde;
    }
}

