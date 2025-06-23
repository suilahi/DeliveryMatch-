package org.DeliveryMatch.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conducteur extends Utilisateur {

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"conducteur"})
    private List<Annonce> annonces;

    public List<Annonce> getAnnonces() { return annonces; }
    public void setAnnonces(List<Annonce> annonces) { this.annonces = annonces; }
}
