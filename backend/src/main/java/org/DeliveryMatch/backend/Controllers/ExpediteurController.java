package org.DeliveryMatch.backend.Controllers;

import org.DeliveryMatch.backend.Model.Colis;
import org.DeliveryMatch.backend.Model.DemandeTransport;
import org.DeliveryMatch.backend.Services.ExpediteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/expediteur")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpediteurController {

    @Autowired
    private ExpediteurService expediteurService;

    // Envoyer une demande de transport
    @PostMapping("/Add")
    public DemandeTransport envoyerDemande(@PathVariable Long expediteurId,
                                           @PathVariable Long annonceId,
                                           @RequestBody DemandeTransport demande) {
        return expediteurService.envoyerDemande(expediteurId, annonceId, demande);
    }

    // Récupérer les demandes d’un expéditeur
    @GetMapping("/demandes")
    public List<DemandeTransport> getDemandesByExpediteur(@PathVariable Long expediteurId) {
        return expediteurService.getDemandesByExpediteur(expediteurId);
    }

    // Récupérer les colis d’un expéditeur
    @GetMapping("/colis")
    public List<Colis> getColisByExpediteur(@PathVariable Long expediteurId) {
        return expediteurService.getColisByExpediteur(expediteurId);
    }

}
