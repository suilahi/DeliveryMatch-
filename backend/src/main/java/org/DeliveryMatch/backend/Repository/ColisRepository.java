package org.DeliveryMatch.backend.Repository;

import org.DeliveryMatch.backend.Model.Colis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, Long> {
    List<Colis> findByDemande_Expediteur_Id(Long expediteurId);

}
