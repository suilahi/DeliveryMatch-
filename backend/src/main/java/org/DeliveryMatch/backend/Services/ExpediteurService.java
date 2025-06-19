package org.DeliveryMatch.backend.Services;

import org.DeliveryMatch.backend.Model.Annonce;
import org.DeliveryMatch.backend.Model.Colis;
import org.DeliveryMatch.backend.Model.DemandeTransport;
import org.DeliveryMatch.backend.Model.Expediteur;
import org.DeliveryMatch.backend.Repository.AnnonceRepository;
import org.DeliveryMatch.backend.Repository.ColisRepository;
import org.DeliveryMatch.backend.Repository.DemandeTransportRepository;
import org.DeliveryMatch.backend.Repository.ExpediteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ExpediteurService {

    @Autowired
    private ExpediteurRepository expediteurRepository;

    @Autowired
    private DemandeTransportRepository demandeTransportRepository;

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    public DemandeTransport envoyerDemande(Long expediteurId, Long annonceId, DemandeTransport demande) {
        Expediteur expediteur = expediteurRepository.findById(expediteurId)
                .orElseThrow(() -> new RuntimeException("Expéditeur introuvable"));

        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        demande.setExpediteur(expediteur);
        demande.setAnnonce(annonce);

        Colis colis = demande.getColis();
        if (colis != null) {
            colis.setDemande(demande);
        }

        return demandeTransportRepository.save(demande);
    }

    public List<DemandeTransport> getDemandesByExpediteur(Long expediteurId) {
        return demandeTransportRepository.findByExpediteurId(expediteurId);
    }

    public List<Colis> getColisByExpediteur(Long expediteurId) {
        return colisRepository.findByDemande_Expediteur_Id(expediteurId);
    }

}
