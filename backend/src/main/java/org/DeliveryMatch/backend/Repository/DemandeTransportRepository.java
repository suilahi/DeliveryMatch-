package org.DeliveryMatch.backend.Repository;

import org.DeliveryMatch.backend.Model.DemandeTransport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeTransportRepository extends JpaRepository<DemandeTransport, Long> {
    List<DemandeTransport> findByExpediteurId(Long expediteurId);
    List<DemandeTransport> findByAnnonceId(Long annonceId);
}
