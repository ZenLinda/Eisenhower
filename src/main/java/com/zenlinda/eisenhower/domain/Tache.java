package com.zenlinda.eisenhower.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Tache.
 */
@Entity
@Table(name = "tache")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "descritpion")
    private String descritpion;

    @Column(name = "date_ideale")
    private LocalDate dateIdeale;

    @Column(name = "date_alerte")
    private LocalDate dateAlerte;

    @Column(name = "date_delai")
    private LocalDate dateDelai;

    @ManyToOne
    @JsonIgnoreProperties("taches")
    private DegreImportance degreImportance;

    @ManyToOne
    @JsonIgnoreProperties("taches")
    private DegreUrgence degreUrgence;

    @ManyToOne
    @JsonIgnoreProperties("taches")
    private Matrice matrice;

    @ManyToOne
    @JsonIgnoreProperties("taches")
    private CategorieTache categorie;

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

    public Tache libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public Tache descritpion(String descritpion) {
        this.descritpion = descritpion;
        return this;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public LocalDate getDateIdeale() {
        return dateIdeale;
    }

    public Tache dateIdeale(LocalDate dateIdeale) {
        this.dateIdeale = dateIdeale;
        return this;
    }

    public void setDateIdeale(LocalDate dateIdeale) {
        this.dateIdeale = dateIdeale;
    }

    public LocalDate getDateAlerte() {
        return dateAlerte;
    }

    public Tache dateAlerte(LocalDate dateAlerte) {
        this.dateAlerte = dateAlerte;
        return this;
    }

    public void setDateAlerte(LocalDate dateAlerte) {
        this.dateAlerte = dateAlerte;
    }

    public LocalDate getDateDelai() {
        return dateDelai;
    }

    public Tache dateDelai(LocalDate dateDelai) {
        this.dateDelai = dateDelai;
        return this;
    }

    public void setDateDelai(LocalDate dateDelai) {
        this.dateDelai = dateDelai;
    }

    public DegreImportance getDegreImportance() {
        return degreImportance;
    }

    public Tache degreImportance(DegreImportance degreImportance) {
        this.degreImportance = degreImportance;
        return this;
    }

    public void setDegreImportance(DegreImportance degreImportance) {
        this.degreImportance = degreImportance;
    }

    public DegreUrgence getDegreUrgence() {
        return degreUrgence;
    }

    public Tache degreUrgence(DegreUrgence degreUrgence) {
        this.degreUrgence = degreUrgence;
        return this;
    }

    public void setDegreUrgence(DegreUrgence degreUrgence) {
        this.degreUrgence = degreUrgence;
    }

    public Matrice getMatrice() {
        return matrice;
    }

    public Tache matrice(Matrice matrice) {
        this.matrice = matrice;
        return this;
    }

    public void setMatrice(Matrice matrice) {
        this.matrice = matrice;
    }

    public CategorieTache getCategorie() {
        return categorie;
    }

    public Tache categorie(CategorieTache categorieTache) {
        this.categorie = categorieTache;
        return this;
    }

    public void setCategorie(CategorieTache categorieTache) {
        this.categorie = categorieTache;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tache)) {
            return false;
        }
        return id != null && id.equals(((Tache) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tache{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", descritpion='" + getDescritpion() + "'" +
            ", dateIdeale='" + getDateIdeale() + "'" +
            ", dateAlerte='" + getDateAlerte() + "'" +
            ", dateDelai='" + getDateDelai() + "'" +
            "}";
    }
}
