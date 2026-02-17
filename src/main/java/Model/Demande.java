package Model;


import Enums.CauseRefus;
import Enums.StatutDemande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    private Article article;

    private int QualityRequired;

    private LocalDate dateTime;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;

    @Enumerated(EnumType.STRING)
    private CauseRefus causeRefus;

    private String commentaire; //more details for causeRefus
}
