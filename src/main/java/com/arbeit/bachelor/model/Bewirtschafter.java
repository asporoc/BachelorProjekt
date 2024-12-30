package com.arbeit.bachelor.model;


import jakarta.persistence.*;

import java.util.List;

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

    public List<TreeNode> getSbks() {
        return sbks;
    }

    public void setSbks(List<TreeNode> sbks) {
        this.sbks = sbks;
    }

    public Behoerde getBehoerde() {
        return behoerde;
    }

    public void setBehoerde(Behoerde behoerde) {
        this.behoerde = behoerde;
    }
    @Transient
    private List<TreeNode> sbks;
}

