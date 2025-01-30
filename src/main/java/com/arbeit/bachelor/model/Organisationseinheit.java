package com.arbeit.bachelor.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Organisationseinheit {
    @Id
    private String name;

    @ManyToOne
    @JoinColumn(name = "behoerde")
    private Behoerde behoerde;

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

    public List<Bewirtschafter> getBewirtschafter() {
        return bewirtschafter;
    }

    public void setBewirtschafter(List<Bewirtschafter> bewirtschafter) {
        this.bewirtschafter = bewirtschafter;
    }

    @OneToMany(mappedBy = "organisationseinheit")
    private List<Bewirtschafter> bewirtschafter;
}

