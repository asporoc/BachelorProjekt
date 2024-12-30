package com.arbeit.bachelor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Behoerde {
    @Id
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "behoerde")
    private List<Organisationseinheit> organisationseinheiten;

    @OneToMany(mappedBy = "behoerde")
    private List<Bewirtschafter> bewirtschafter;
}
