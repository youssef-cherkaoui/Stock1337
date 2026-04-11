package stock1337.stock1337.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stock1337.stock1337.enums.HistoryType;
import stock1337.stock1337.model.Article;
import stock1337.stock1337.model.Stock;
import stock1337.stock1337.model.User;
import stock1337.stock1337.model.stockHistory;
import stock1337.stock1337.repository.ArticleRepository;
import stock1337.stock1337.repository.StockHistoryRepository;
import stock1337.stock1337.repository.StockRepository;
import stock1337.stock1337.repository.UserRepository;
import stock1337.stock1337.service.StockHistoryService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StockHistoryServiceImpl implements StockHistoryService {
    private final UserRepository userRepo;
    private final ArticleRepository articleRepo;
    private final StockRepository stockRepo;
    private final StockHistoryRepository historyRepo;

    @Override
    @Transactional
    public void recordHistory(Long articleId, Long stockId,
                              Integer quantityChange,
                              HistoryType type, String reason) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        Article article = articleRepo.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found: " + articleId));

        Stock stock;
        if (stockId != null) {
            stock = stockRepo.findById(stockId)
                    .orElseThrow(() -> new RuntimeException("Stock not found: " + stockId));
        } else {
            stock = article.getStock();
            if (stock == null) {
                throw new RuntimeException("Article n'a pas de stock");
            }
        }

        stockHistory history = stockHistory.builder()
                .article(article)
                .stock(stock)
                .user(user)
                .quantityChange(quantityChange)
                .type(type)
                .reason(reason)
                .recordedAt(LocalDateTime.now())
                .build();

        historyRepo.save(history);
    }
}
