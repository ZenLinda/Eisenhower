package com.zenlinda.eisenhower.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A DegreUrgence.
 */
@Entity
@Table(name = "degre_urgence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DegreUrgence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ordre", nullable = false)
    private Integer ordre;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "degreUrgence")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tache> taches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public DegreUrgence ordre(Integer ordre) {
        this.ordre = ordre;
        return this;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getLibelle() {
        return libelle;
    }

    public DegreUrgence libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public DegreUrgence taches(Set<Tache> taches) {
        this.taches = taches;
        return this;
    }

    public DegreUrgence addTache(Tache tache) {
        this.taches.add(tache);
        tache.setDegreUrgence(this);
        return this;
    }

    public DegreUrgence removeTache(Tache tache) {
        this.taches.remove(tache);
        tache.setDegreUrgence(null);
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
        if (!(o instanceof DegreUrgence)) {
            return false;
        }
        return id != null && id.equals(((DegreUrgence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DegreUrgence{" +
            "id=" + getId() +
            ", ordre=" + getOrdre() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
