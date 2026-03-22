package stock1337.stock1337.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock1337.stock1337.model.stockHistory;
import stock1337.stock1337.repository.StockHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/history")
@RequiredArgsConstructor
public class StockHistoryController {

    private final StockHistoryRepository stockHistoryRepository;

    @GetMapping("/recent")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<stockHistory>> getRecentHistory() {
        LocalDateTime from  = LocalDateTime.now().minusDays(30);
        return ResponseEntity.ok(stockHistoryRepository.findRecentHistory(from));
    }

    @GetMapping("/article/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<stockHistory>> getArticleHistory(@PathVariable Long id) {
        return ResponseEntity.ok(stockHistoryRepository.findByArticle_IdOrderByRecordedAtAsc(id));
    }
}
