package stock1337.stock1337.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stock1337.stock1337.Model.Article;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByName(String name);
    List<Article> findByDepartementId(Long departementId);

    @Query("select a from Article a where a.quantity <= a.minThreshold")
    List<Article> FindLowStockArticles();

    List<Article> findByStockId(Long stockId);
}
