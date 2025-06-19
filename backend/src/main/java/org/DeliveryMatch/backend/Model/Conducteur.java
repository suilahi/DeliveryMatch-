package org.DeliveryMatch.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernatelazyInitializer","handler"})
public class Conducteur extends Utilisateur {

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    @OneToMany(mappedBy = "conducteur")
    private List<Annonce> annonces;

}
