package org.DeliveryMatch.backend.Controllers;


import org.DeliveryMatch.backend.Model.Annonce;
import org.DeliveryMatch.backend.Model.Colis;
import org.DeliveryMatch.backend.Model.DemandeTransport;
import org.DeliveryMatch.backend.Services.ConducteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ConducteurController {

    @Autowired
    private ConducteurService conducteurService;

    //@PreAuthorize("hasRole('CONDUCTEUR')")
    @PostMapping("Add/{conducteurId}")
    public Annonce publierAnnonce(@PathVariable Long conducteurId, @RequestBody Annonce annonce) {
        return conducteurService.publierAnnonce(conducteurId, annonce);
    }

    @GetMapping("/conducteurs/{conducteurId}/annonces")
    public List<Annonce> getAnnoncesParConducteur(@PathVariable Long conducteurId) {
        return conducteurService.getAnnoncesParConducteur(conducteurId);
    }

    @GetMapping("/annonces/{annonceId}/demandes")
    public List<DemandeTransport> getDemandesParAnnonce(@PathVariable Long annonceId) {
        return conducteurService.getDemandesParAnnonce(annonceId);
    }

    @PutMapping("/demandes/{demandeId}/accepter")
    public Optional<DemandeTransport> accepterDemande(@PathVariable Long demandeId) {
        return conducteurService.accepterDemande(demandeId);
    }

    @PutMapping("/demandes/{demandeId}/refuser")
    public Optional<DemandeTransport> refuserDemande(@PathVariable Long demandeId) {
        return conducteurService.refuserDemande(demandeId);
    }

    @GetMapping("/conducteurs/{conducteurId}/historique-annonces")
    public List<Annonce> getHistoriqueAnnonces(@PathVariable Long conducteurId) {
        return conducteurService.getHistoriqueAnnonces(conducteurId);
    }

    @GetMapping("/conducteurs/{conducteurId}/colis")
    public List<Colis> getColisTransportesParConducteur(@PathVariable Long conducteurId) {
        return conducteurService.getColisTransportesParConducteur(conducteurId);
    }

    @PutMapping("/annonces/{id}")
    public ResponseEntity<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonce) {
        Annonce updated = conducteurService.updateAnnonce(id, annonce);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/annonces/{id}")
    public ResponseEntity<?> deleteAnnonce(@PathVariable Long id) {
        conducteurService.deleteAnnonce(id);
        return ResponseEntity.ok().build();
    }
}
