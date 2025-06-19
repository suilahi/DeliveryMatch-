package org.DeliveryMatch.backend.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Expediteur extends Utilisateur {

    public List<DemandeTransport> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeTransport> demandes) {
        this.demandes = demandes;
    }

    @OneToMany(mappedBy = "expediteur")
    private List<DemandeTransport> demandes;
}
