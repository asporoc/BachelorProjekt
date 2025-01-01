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

    public List<Organisationseinheit> getOrganisationseinheiten() {
        return organisationseinheiten;
    }

    public void setOrganisationseinheiten(List<Organisationseinheit> organisationseinheiten) {
        this.organisationseinheiten = organisationseinheiten;
    }

    public List<Bewirtschafter> getBewirtschafter() {
        return bewirtschafter;
    }

    public void setBewirtschafter(List<Bewirtschafter> bewirtschafter) {
        this.bewirtschafter = bewirtschafter;
    }

    @OneToMany(mappedBy = "behoerde")
    private List<Bewirtschafter> bewirtschafter;
}
