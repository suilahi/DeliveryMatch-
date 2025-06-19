package org.DeliveryMatch.backend.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandeTransport {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public Expediteur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Expediteur expediteur) {
        this.expediteur = expediteur;
    }

    public Colis getColis() {
        return colis;
    }

    public void setColis(Colis colis) {
        this.colis = colis;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;

    @ManyToOne
    @JoinColumn(name = "annonce_id")
    private Annonce annonce;

    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private Expediteur expediteur;

    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL)
    private Colis colis;
}
