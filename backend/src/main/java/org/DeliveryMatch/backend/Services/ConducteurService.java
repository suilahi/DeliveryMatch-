package org.DeliveryMatch.backend.Services;


import org.DeliveryMatch.backend.Model.*;
import org.DeliveryMatch.backend.Repository.AnnonceRepository;
import org.DeliveryMatch.backend.Repository.ConducteurReposiroty;
import org.DeliveryMatch.backend.Repository.DemandeTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConducteurService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private DemandeTransportRepository demandeRepository;

    @Autowired
    private ConducteurReposiroty conducteurReposiroty;

    public Annonce publierAnnonce(Long conducteurId, Annonce annonce) {
        Conducteur conducteur = conducteurReposiroty.findById(conducteurId)
                .orElseThrow(() -> new RuntimeException("Conducteur non trouvé"));

        annonce.setConducteur(conducteur);
        return annonceRepository.save(annonce);
    }

    public List<Annonce> getAnnoncesParConducteur(Long conducteurId) {
        return annonceRepository.findByConducteurId(conducteurId);
    }

    public List<DemandeTransport> getDemandesParAnnonce(Long annonceId) {
        return demandeRepository.findByAnnonceId(annonceId);
    }

    public Optional<DemandeTransport> accepterDemande(Long demandeId) {
        Optional<DemandeTransport> demandeOpt = demandeRepository.findById(demandeId);
        demandeOpt.ifPresent(demande -> {
            demande.setStatut(Statut.ACCEPTE);
            demandeRepository.save(demande);
        });
        return demandeOpt;
    }

    public Optional<DemandeTransport> refuserDemande(Long demandeId) {
        Optional<DemandeTransport> demandeOpt = demandeRepository.findById(demandeId);
        demandeOpt.ifPresent(demande -> {
            demande.setStatut(Statut.REFUSE);
            demandeRepository.save(demande);
        });
        return demandeOpt;
    }

    public List<Annonce> getHistoriqueAnnonces(Long conducteurId) {
        return annonceRepository.findByConducteurId(conducteurId); // filtrer côté frontend ou ajouter un champ "terminé"
    }

    public List<Colis> getColisTransportesParConducteur(Long conducteurId) {
        List<Annonce> annonces = annonceRepository.findByConducteurId(conducteurId);
        return annonces.stream()
                .flatMap(annonce -> annonce.getDemandes().stream())
                .filter(demande -> demande.getStatut() == Statut.ACCEPTE || demande.getStatut() == Statut.ACCEPTE)
                .map(DemandeTransport::getColis)
                .toList();
    }


    public void deleteAnnonce(Long Annonceid) {
        annonceRepository.deleteById(Annonceid);
    }
    public Annonce updateAnnonce(Long Annonceid, Annonce updatedAnnonce) {
        return annonceRepository.findById(Annonceid).map(a -> {
            a.setLieuDepart(updatedAnnonce.getLieuDepart());
            a.setDestination(updatedAnnonce.getDestination());
            a.setDimensionsMax(updatedAnnonce.getDimensionsMax());
            a.setCapaciteDisponible(updatedAnnonce.getCapaciteDisponible());
            a.setTypeMarchandise(updatedAnnonce.getTypeMarchandise());
            a.setEtapes(updatedAnnonce.getEtapes());
            return annonceRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
    }
}
