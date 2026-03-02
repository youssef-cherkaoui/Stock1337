package stock1337.stock1337.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock1337.stock1337.enums.CauseRefus;
import stock1337.stock1337.enums.StatutDemande;
import stock1337.stock1337.model.Article;
import stock1337.stock1337.model.Demande;
import stock1337.stock1337.model.User;
import stock1337.stock1337.repository.ArticleRepository;
import stock1337.stock1337.repository.DemandeRepository;
import stock1337.stock1337.repository.UserRepository;
import stock1337.stock1337.service.DemandeService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class DemandeServiceImpl implements DemandeService {


    private final DemandeRepository demandeRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    public Demande createDemande(String email, Long IdArticle, Integer quantity) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Article article = articleRepository.findById(IdArticle)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Demande demande = new Demande();
        demande.setUser(user);
        demande.setArticle(article);
        demande.setQuantityRequired(quantity);
        demande.setStatut(StatutDemande.EN_ATTENTE);
        return demandeRepository.save(demande);
    }

    @Override
    public Demande aprouveDemande(Long IdDemande) {

        Demande demande = demandeRepository.findById(IdDemande)
                .orElseThrow(() -> new RuntimeException("Demande not found"));

        Article article = demande.getArticle();
        if(article.getQuantity() < demande.getQuantityRequired()){
            throw new RuntimeException("Quality insuffisante");
        }

        article.setQuantity(article.getQuantity() - demande.getQuantityRequired());
        demande.setStatut(StatutDemande.ACCEPTEE);

        articleRepository.save(article);
        return demandeRepository.save(demande);
    }

    @Override
    public Demande rejectDemande(Long IdDemande, CauseRefus cause) {

        Demande demande = demandeRepository.findById(IdDemande)
                .orElseThrow(() -> new RuntimeException("Demande not found"));

        demande.setStatut(StatutDemande.REFUSEE);
        demande.setCauseRefus(cause);

        return demandeRepository.save(demande);
    }

    @Override
    public List<Demande> getPendingDemandes() {
        return demandeRepository.findByStatut(StatutDemande.EN_ATTENTE);
    }

    @Override
    public List<Demande> getUserDemandes(Long IdUser) {
        return demandeRepository.findByUserId(IdUser);
    }

    @Override
    public List<Demande> getDemandesByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return demandeRepository.findByUserId(user.getId());
    }
}
