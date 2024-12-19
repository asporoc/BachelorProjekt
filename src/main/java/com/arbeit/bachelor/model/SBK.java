package com.arbeit.bachelor.model;

import jakarta.persistence.*;

@Entity
public class SBK {
    @Id
    private String id;

    private String haushaltsnummer;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SBK parent;

    @ManyToOne
    @JoinColumn(name = "bewirtschafter")
    private Bewirtschafter bewirtschafter;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHaushaltsnummer() {
        return haushaltsnummer;
    }

    public void setHaushaltsnummer(String haushaltsnummer) {
        this.haushaltsnummer = haushaltsnummer;
    }

    public SBK getParent() {
        return parent;
    }

    public void setParent(SBK parent) {
        this.parent = parent;
    }

    public Bewirtschafter getBewirtschafter() {
        return bewirtschafter;
    }

    public void setBewirtschafter(Bewirtschafter bewirtschafter) {
        this.bewirtschafter = bewirtschafter;
    }
}

