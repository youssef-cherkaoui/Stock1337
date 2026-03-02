package stock1337.stock1337.service;

import stock1337.stock1337.dto.ArticleRequest;
import stock1337.stock1337.model.Article;

import java.util.List;

public interface ArticleService {
    Article createArticle (ArticleRequest request);
    List<Article> searchArticle(Long stockId, Long departementID);
    List<Article> getLowStockArticles();
}
