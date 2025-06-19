package org.DeliveryMatch.backend.Repository;

import org.DeliveryMatch.backend.Model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByConducteurId(Long conducteurId);
    List<Annonce> getAnnonceById(Long AnnonceId);
}
