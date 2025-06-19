package org.DeliveryMatch.backend.Repository;

import org.DeliveryMatch.backend.Model.Annonce;
import org.DeliveryMatch.backend.Model.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConducteurReposiroty extends JpaRepository<Conducteur, Long> {

}
