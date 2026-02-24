package stock1337.stock1337.Service;

import stock1337.stock1337.DTO.ArticleRequest;
import stock1337.stock1337.Model.Article;

import java.util.List;

public interface ArticleService {
    Article createArticle (ArticleRequest request);
    List<Article> searchArticle(Long stockId, Long departementID);
    List<Article> getLowStockArticles();
}
