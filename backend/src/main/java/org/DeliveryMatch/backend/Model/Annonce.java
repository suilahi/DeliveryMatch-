package org.DeliveryMatch.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "annonce")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "conducteur", "demandes"})
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lieuDepart;
    private String destination;
    private String etapes;
    private String typeMarchandise;

    private double capaciteDisponible;
    private double dimensionsMax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conducteur_id")
    @JsonIgnoreProperties({"annonces", "hibernateLazyInitializer", "handler"})
    private Conducteur conducteur;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DemandeTransport> demandes;

    public Annonce() {}

    // Getters et setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLieuDepart() { return lieuDepart; }
    public void setLieuDepart(String lieuDepart) { this.lieuDepart = lieuDepart; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getEtapes() { return etapes; }
    public void setEtapes(String etapes) { this.etapes = etapes; }

    public String getTypeMarchandise() { return typeMarchandise; }
    public void setTypeMarchandise(String typeMarchandise) { this.typeMarchandise = typeMarchandise; }

    public double getCapaciteDisponible() { return capaciteDisponible; }
    public void setCapaciteDisponible(double capaciteDisponible) { this.capaciteDisponible = capaciteDisponible; }

    public double getDimensionsMax() { return dimensionsMax; }
    public void setDimensionsMax(double dimensionsMax) { this.dimensionsMax = dimensionsMax; }

    public Conducteur getConducteur() { return conducteur; }
    public void setConducteur(Conducteur conducteur) { this.conducteur = conducteur; }

    public List<DemandeTransport> getDemandes() { return demandes; }
    public void setDemandes(List<DemandeTransport> demandes) { this.demandes = demandes; }
}
