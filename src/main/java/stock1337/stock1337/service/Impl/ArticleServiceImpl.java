package stock1337.stock1337.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock1337.stock1337.dto.ArticleRequest;
import stock1337.stock1337.enums.HistoryType;
import stock1337.stock1337.model.Article;
import stock1337.stock1337.model.Departement;
import stock1337.stock1337.model.Stock;
import stock1337.stock1337.repository.ArticleRepository;
import stock1337.stock1337.repository.DepartementRepository;
import stock1337.stock1337.repository.StockRepository;
import stock1337.stock1337.service.ArticleService;
import stock1337.stock1337.service.EmailAlertService;
import stock1337.stock1337.service.StockHistoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {


    private final ArticleRepository articleRepository;
    private final StockRepository stockRepository;
    private final DepartementRepository departementRepository;
    private final EmailAlertService emailAlertService;
    private final ArticleRepository articleRepo;
    private final StockHistoryService stockHistoryService;

    @Override
    public Article createArticle(ArticleRequest request) {

        Stock stock = stockRepository.findById(request.getStockId()).
                orElseThrow(() -> new RuntimeException("Stock not found"));

        Departement dep = departementRepository.findById(request.getDepartementId()
                ).orElseThrow(() -> new RuntimeException("Departement not found"));

        Article article = new Article();
        article.setName(request.getName());
        article.setDescription(request.getDescription());
        article.setQuantity(request.getQuantity());
        article.setMinThreshold(request.getMinThreshold());
        article.setStock(stock);
        article.setDepartement(dep);

        Article saved = articleRepository.save(article);

        emailAlertService.checkAndSendAlert(saved.getId());

        return saved;
    }

    @Override
    public List<Article> searchArticle(Long stockId, Long departementID) {

        if(stockId != null ) return articleRepository.findByStockId(stockId);
        if(departementID != null) return articleRepository.findByDepartementId(departementID);
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getLowStockArticles() {
        return articleRepository.FindLowStockArticles();
    }


    @Override
    @Transactional
    public Article updateQuantity(Long articleId, int newQuantity){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        article.setQuantity(newQuantity);
        Article update = articleRepository.save(article);

        emailAlertService.checkAndSendAlert(update.getId());

        return update;
    }

    @Override
    @Transactional
    public Article updateArticle(Long id, ArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found" + id));

        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Departement dep = departementRepository.findById(request.getDepartementId())
                .orElseThrow(() -> new RuntimeException("Departement not found"));

        article.setName(request.getName());
        article.setDescription(request.getDescription());
        article.setQuantity(request.getQuantity());
        article.setMinThreshold(request.getMinThreshold());
        article.setStock(stock);
        article.setDepartement(dep);

        Article update = articleRepository.save(article);

        emailAlertService.checkAndSendAlert(update.getId());

        return update;
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found" + id));
        articleRepository.delete(article);

    }


    public void addStock(Long articleId, Long stockId, int quantity, String reason) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));


        article.setQuantity(article.getQuantity() + quantity);
        articleRepo.save(article);

        stockHistoryService.recordHistory(
                articleId,
                stockId,
                quantity,
                HistoryType.ENTREE,
                reason
        );
    }


}
