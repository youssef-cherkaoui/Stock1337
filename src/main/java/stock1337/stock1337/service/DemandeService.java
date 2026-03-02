package stock1337.stock1337.service;

import stock1337.stock1337.enums.CauseRefus;
import stock1337.stock1337.model.Demande;

import java.util.List;

public interface DemandeService {



    Demande createDemande(String email,Long IdArticle, Integer quantity);
    Demande aprouveDemande(Long IdDemande);
    Demande rejectDemande(Long IdDemande, CauseRefus cause);
    List<Demande> getPendingDemandes();
    List<Demande> getUserDemandes(Long IdUser);
    List<Demande> getDemandesByEmail(String email);
}
