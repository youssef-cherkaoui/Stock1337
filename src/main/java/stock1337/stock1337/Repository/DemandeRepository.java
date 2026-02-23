package stock1337.stock1337.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock1337.stock1337.Enums.StatutDemande;
import stock1337.stock1337.Model.Demande;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByStatut(StatutDemande status);
    List<Demande> findByUserId(Long idUser);
}
