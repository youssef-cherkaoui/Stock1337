package stock1337.stock1337.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stock1337.stock1337.model.stockHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<stockHistory, Long> {

    @EntityGraph(attributePaths = {"article", "stock", "user"})
    @Query("SELECT h FROM stockHistory h ORDER BY h.recordedAt DESC")
    List<stockHistory> findAllByOrderByRecordedAtDesc();

    @EntityGraph(attributePaths = {"article", "stock", "user"})
    @Query("SELECT sh FROM stockHistory sh WHERE sh.recordedAt >= :from ORDER BY sh.recordedAt DESC")
    List<stockHistory> findRecentHistory(@Param("from") LocalDateTime from);

    @EntityGraph(attributePaths = {"article", "stock", "user"})
    @Query("SELECT sh FROM stockHistory sh WHERE sh.article.id = :articleId ORDER BY sh.recordedAt ASC")
    List<stockHistory> findByArticle_IdOrderByRecordedAtAsc(Long articleId);
}
