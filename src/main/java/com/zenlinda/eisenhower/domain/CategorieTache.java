package com.zenlinda.eisenhower.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.zenlinda.eisenhower.domain.enumeration.Couleur;

/**
 * A CategorieTache.
 */
@Entity
@Table(name = "categorie_tache")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategorieTache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "couleur", nullable = false)
    private Couleur couleur;

    @OneToMany(mappedBy = "categorie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tache> taches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public CategorieTache libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public CategorieTache couleur(Couleur couleur) {
        this.couleur = couleur;
        return this;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public CategorieTache taches(Set<Tache> taches) {
        this.taches = taches;
        return this;
    }

    public CategorieTache addTache(Tache tache) {
        this.taches.add(tache);
        tache.setCategorie(this);
        return this;
    }

    public CategorieTache removeTache(Tache tache) {
        this.taches.remove(tache);
        tache.setCategorie(null);
        return this;
    }

    public void setTaches(Set<Tache> taches) {
        this.taches = taches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieTache)) {
            return false;
        }
        return id != null && id.equals(((CategorieTache) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategorieTache{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", couleur='" + getCouleur() + "'" +
            "}";
    }
}
