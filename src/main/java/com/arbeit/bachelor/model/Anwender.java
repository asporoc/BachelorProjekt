package com.arbeit.bachelor.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
public class Anwender {
    @Id
    private String bezeichnung;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rolle rolle;

    @ManyToOne
    @JoinColumn(name = "behoerde")
    private Behoerde behoerde;

    @ManyToOne
    @JoinColumn(name = "organisationseinheit")
    private Organisationseinheit organisationseinheit;

    @OneToOne
    @JoinColumn(name = "bewirtschafter")
    private Bewirtschafter bewirtschafter;

    @Transient
    private Map<TreeNode, Permissions> acl;

    public Map<TreeNode, Permissions> getAcl() {
        return acl;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    public Behoerde getBehoerde() {
        return behoerde;
    }

    public void setBehoerde(Behoerde behoerde) {
        this.behoerde = behoerde;
    }

    public Organisationseinheit getOrganisationseinheit() {
        return organisationseinheit;
    }

    public void setOrganisationseinheit(Organisationseinheit organisationseinheit) {
        this.organisationseinheit = organisationseinheit;
    }

    public Bewirtschafter getBewirtschafter() {
        return bewirtschafter;
    }

    public void setBewirtschafter(Bewirtschafter bewirtschafter) {
        this.bewirtschafter = bewirtschafter;
    }

    public void setAcl(Map<TreeNode, Permissions> acl) {
        this.acl = acl;
    }
}
