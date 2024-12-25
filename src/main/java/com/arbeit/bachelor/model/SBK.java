package com.arbeit.bachelor.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Transient // only in memory
    private List<SBK> children = new ArrayList<>();


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

    public List<SBK> getChildren() {
        return children;
    }

    public void setChildren(List<SBK> children) {
        this.children = children;
    }
}

