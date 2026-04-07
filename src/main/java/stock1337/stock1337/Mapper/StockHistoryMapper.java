package stock1337.stock1337.Mapper;


import org.springframework.stereotype.Component;
import stock1337.stock1337.dto.stockHistoryDTO;
import stock1337.stock1337.enums.HistoryType;
import stock1337.stock1337.model.stockHistory;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockHistoryMapper {

    public stockHistoryDTO toDTO(stockHistory history) {
        if (history == null) {
            return null;
        }

        return stockHistoryDTO.builder()
                .id(history.getId())
                .articleId(history.getArticle() != null ? history.getArticle().getId() : null)
                .articleName(history.getArticle() != null ? history.getArticle().getName() : "Article supprimé")
                .stockName(history.getStock() != null ? history.getStock().getName() : "Stock non spécifié")
                .userName(history.getUser() != null ? history.getUser().getName() : "Système")
                .quantityChange(history.getQuantityChange() != null ? history.getQuantityChange() : 0)
                .type(history.getType() != null ? history.getType() : HistoryType.AJUSTEMENT)
                .reason(history.getReason() != null ? history.getReason() : "Non spécifié")
                .recordedAt(history.getRecordedAt())
                .hasUser(history.getUser() != null)
                .hasStock(history.getStock() != null)
                .build();
    }

    public List<stockHistoryDTO> toDTOList(List<stockHistory> histories) {
        return histories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
