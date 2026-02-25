package stock1337.stock1337.TestService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stock1337.stock1337.Enums.CauseRefus;
import stock1337.stock1337.Enums.StatutDemande;
import stock1337.stock1337.Model.Article;
import stock1337.stock1337.Model.Demande;
import stock1337.stock1337.Model.User;
import stock1337.stock1337.Repository.ArticleRepository;
import stock1337.stock1337.Repository.DemandeRepository;
import stock1337.stock1337.Repository.UserRepository;
import stock1337.stock1337.Service.DemandeService;
import stock1337.stock1337.Service.Impl.DemandeServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DemandeServiceTest {

    @Mock
    private DemandeRepository demandeRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DemandeServiceImpl demandeService;

    private User user;
    private Demande demande;
    private Article article;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("User@1337.ma");

        article = new Article();
        article.setId(10L);
        article.setName("Clavier");
        article.setQuantity(50);

        demande = new Demande();
        demande.setId(100L);
        demande.setArticle(article);
        demande.setQuantityRequired(10);
        demande.setStatut(StatutDemande.EN_ATTENTE);
    }


    @Test
    void ShouldCreateDemandeSuccessfully() {
        when(userRepository.findByEmail("User@1337.ma")).thenReturn(Optional.of(user));
        when(articleRepository.findById(10L)).thenReturn(Optional.of(article));
        when(demandeRepository.save(any(Demande.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Demande result = demandeService.createDemande("User@1337.ma",10L,5);

        assertNotNull(result);
        assertEquals(StatutDemande.EN_ATTENTE, result.getStatut());
        assertEquals(user, result.getUser());
        verify(demandeRepository).save(any(Demande.class));
    }

    @Test
    void ShouldApproveDemandeAndReduceStock(){
        when(demandeRepository.findById(100L)).thenReturn(Optional.of(demande));

        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(demandeRepository.save(any(Demande.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Demande approved = demandeService.aprouveDemande(100L);

        assertNotNull(approved);
        assertEquals(StatutDemande.ACCEPTEE, approved.getStatut());
        assertEquals(40, article.getQuantity());
        verify(articleRepository).save(article);
        verify(demandeRepository).save(approved);
    }

    @Test
    void ShouldThrowExceptionWhenStockIsInsufficient(){
        demande.setQuantityRequired(100);
        when(demandeRepository.findById(100L)).thenReturn(Optional.of(demande));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            demandeService.aprouveDemande(100L);
        });

        assertEquals("Quality insuffisante", exception.getMessage());
        verify(articleRepository, never()).save(any());
    }

    @Test
    void ShouldRejectDemandeWithCause(){
        when (demandeRepository.findById(100L)).thenReturn(Optional.of(demande));
        when(demandeRepository.save(any(Demande.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Demande rejected = demandeService.rejectDemande(100L, CauseRefus.RUPTURE_STOCK);

        assertNotNull(rejected);
        assertEquals(StatutDemande.REFUSEE, rejected.getStatut());
        assertEquals(CauseRefus.RUPTURE_STOCK, rejected.getCauseRefus());
        verify(demandeRepository).findById(100L);
        verify(demandeRepository).save(rejected);

    }
}
