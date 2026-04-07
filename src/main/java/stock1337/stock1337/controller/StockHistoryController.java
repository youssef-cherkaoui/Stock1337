package stock1337.stock1337.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stock1337.stock1337.dto.stockHistoryDTO;
import stock1337.stock1337.enums.HistoryType;
import stock1337.stock1337.model.stockHistory;
import stock1337.stock1337.repository.StockHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/history")
@RequiredArgsConstructor
public class StockHistoryController {

    private final StockHistoryRepository stockHistoryRepository;

    @GetMapping("/recent")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<stockHistoryDTO>> getRecentHistory(
            @RequestParam(required = false, defaultValue = "false") boolean all) {

        List<stockHistory> historyList;

        if (all) {
            historyList = stockHistoryRepository.findAllByOrderByRecordedAtDesc();
        } else {
            LocalDateTime from = LocalDateTime.now().minusDays(30);
            historyList = stockHistoryRepository.findRecentHistory(from);
        }

        List<stockHistoryDTO> dtos = historyList.stream()
                .filter(h -> h.getStock() != null && h.getUser() != null)  // ← Hna
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/article/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<stockHistoryDTO>> getArticleHistory(@PathVariable Long id) {
        List<stockHistory> historyList = stockHistoryRepository.findByArticle_IdOrderByRecordedAtAsc(id);

        List<stockHistoryDTO> dtos = historyList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    private stockHistoryDTO convertToDTO(stockHistory history) {
        // 7ami l'error - checki ila stock null
        String stockName;
        boolean hasStock;

        if (history.getStock() != null) {
            stockName = history.getStock().getName();
            hasStock = true;
        } else {
            stockName = "Stock inconnu";  // wla "N/A", "-", chi 7aja
            hasStock = false;
        }

        // 7ami l'error - checki ila user null
        String userName;
        boolean hasUser;

        if (history.getUser() != null) {
            userName = history.getUser().getName();
            hasUser = true;
        } else {
            userName = "Utilisateur inconnu";
            hasUser = false;
        }

        // Checki article aussi (pour être sûr)
        Long articleId = null;
        String articleName = "Article inconnu";

        if (history.getArticle() != null) {
            articleId = history.getArticle().getId();
            articleName = history.getArticle().getName();
        }

        return stockHistoryDTO.builder()
                .id(history.getId())
                .articleId(articleId)
                .articleName(articleName)
                .stockName(stockName)
                .userName(userName)
                .hasUser(hasUser)
                .hasStock(hasStock)
                .quantityChange(history.getQuantityChange() != null ? history.getQuantityChange() : 0)
                .type(history.getType() != null ? history.getType() : HistoryType.ENTREE)
                .reason(history.getReason() != null ? history.getReason() : "Non spécifié")
                .recordedAt(history.getRecordedAt())
                .build();
    }
}
