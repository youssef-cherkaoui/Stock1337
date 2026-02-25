package stock1337.stock1337.TestService;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stock1337.stock1337.DTO.ArticleRequest;
import stock1337.stock1337.Model.Article;
import stock1337.stock1337.Model.Departement;
import stock1337.stock1337.Model.Stock;
import stock1337.stock1337.Repository.ArticleRepository;
import stock1337.stock1337.Repository.DepartementRepository;
import stock1337.stock1337.Repository.StockRepository;
import stock1337.stock1337.Service.Impl.ArticleServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private ArticleRequest request;
    private Stock stock;
    private Departement departement;

    @BeforeEach
    void setUp() {
    request = new ArticleRequest();
    request.setName("Laptop");
    request.setDescription("Gaming Laptop");
    request.setQuantity(18);
    request.setMinThreshold(2);
    request.setStockId(1L);
    request.setDepartementId(1L);

    stock = new Stock();
    stock.setId(1L);

    departement = new Departement();
    departement.setId(1L);
    }


    @Test
    void ShouldCreateArticleSuccessfully() {

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(departementRepository.findById(1L)).thenReturn(Optional.of(departement));

        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Article result = articleService.createArticle(request);
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(stock, result.getStock());
        assertEquals(departement, result.getDepartement());

        verify(articleRepository).save(any(Article.class));
    }


    @Test
    void shouldThrowExceptionWhenStockNotFound(){
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> articleService.createArticle(request));
        verify(articleRepository, never()).save(any());
    }

    @Test
    void ShouldSearchArticleByStockId(){
        List<Article> articles = List.of(new Article(), new Article());
        when (articleRepository.findByStockId(1L)).thenReturn(articles);

        List<Article> result = articleService.searchArticle(1L, null);
        assertEquals(2, result.size());
        verify(articleRepository).findByStockId(1L);
    }

    @Test
    void ShouldSearchArticleByDepartementId(){
        List<Article> articles = List.of(new Article());
        when (articleRepository.findByDepartementId(1L)).thenReturn(articles);

        List<Article> result = articleService.searchArticle(null, 1L);
        assertEquals(1, result.size());
        verify(articleRepository).findByDepartementId(1L);
    }

    @Test
    void ShouldReturnAllArticlesWhenParamsAreNull(){
        when(articleRepository.findAll()).thenReturn(new ArrayList<>());

        articleService.searchArticle(null, null);
        verify(articleRepository).findAll();
    }
}
