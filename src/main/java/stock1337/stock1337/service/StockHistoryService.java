package stock1337.stock1337.service;

import org.springframework.stereotype.Service;
import stock1337.stock1337.enums.HistoryType;
import stock1337.stock1337.model.Article;
import stock1337.stock1337.model.Stock;

@Service
public interface StockHistoryService {
    void recordHistory(Long articleId, Long stockId,
                       Integer quantityChange,
                       HistoryType type, String reason);
}






