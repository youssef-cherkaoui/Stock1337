package stock1337.stock1337.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stock1337.stock1337.model.AlertHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Long> {

    @Query("SELECT COUNT(a) > 0 FROM AlertHistory a " +
            "WHERE a.article.id = :articleId " +
            "AND a.alertType = :alertType " +
            "AND a.sentAt > :since")
    boolean existsRecentAlert(@Param("articleId") Long articleId,
                              @Param("alertType") String alertType,
                              @Param("since") LocalDateTime since);


    List<AlertHistory> findBySentAtAfter(LocalDateTime date);
}
