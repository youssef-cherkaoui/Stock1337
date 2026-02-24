package stock1337.stock1337.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock1337.stock1337.DTO.ArticleRequest;
import stock1337.stock1337.Model.Article;
import stock1337.stock1337.Model.Departement;
import stock1337.stock1337.Model.Stock;
import stock1337.stock1337.Repository.ArticleRepository;
import stock1337.stock1337.Repository.DepartementRepository;
import stock1337.stock1337.Repository.StockRepository;
import stock1337.stock1337.Service.ArticleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {


    private final ArticleRepository articleRepository;
    private final StockRepository stockRepository;
    private final DepartementRepository departementRepository;

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

        return articleRepository.save(article);
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
}
