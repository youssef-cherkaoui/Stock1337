package stock1337.stock1337.Service;

import stock1337.stock1337.Enums.CauseRefus;
import stock1337.stock1337.Model.Demande;
import stock1337.stock1337.Model.User;

import java.util.List;

public interface DemandeService {



    Demande createDemande(String email,Long IdArticle, Integer quantity);
    Demande aprouveDemande(Long IdDemande);
    Demande rejectDemande(Long IdDemande, CauseRefus cause);
    List<Demande> getPendingDemandes();
    List<Demande> getUserDemandes(Long IdUser);
    List<Demande> getDemandesByEmail(String email);
}
