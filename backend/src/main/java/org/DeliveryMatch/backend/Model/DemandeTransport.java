package org.DeliveryMatch.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DemandeTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;

    @ManyToOne
    @JoinColumn(name = "annonce_id")
    @JsonIgnoreProperties({"demandes", "hibernateLazyInitializer", "handler"})
    private Annonce annonce;

    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private Expediteur expediteur;

    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL)
    private Colis colis;

}
