package org.DeliveryMatch.backend.Services;

import org.DeliveryMatch.backend.Model.Annonce;
import org.DeliveryMatch.backend.Repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AnnonceRepository annonceRepository;



    public void deleteAnnonce(Long Annonceid) {
        annonceRepository.deleteById(Annonceid);
    }

    public List<Annonce> getAnnonces() {
        return annonceRepository.findAll();
    }

    public Annonce updateAnnonce(Long Annonceid, Annonce updatedAnnonce) {
        return annonceRepository.findById(Annonceid).map(a -> {
            a.setLieuDepart(updatedAnnonce.getLieuDepart());
            a.setDestination(updatedAnnonce.getDestination());
            a.setDimensionsMax(updatedAnnonce.getDimensionsMax());
            a.setCapaciteDisponible(updatedAnnonce.getCapaciteDisponible());
            a.setTypeMarchandise(updatedAnnonce.getTypeMarchandise());
            return annonceRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
    }


}
