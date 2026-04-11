package stock1337.stock1337.Mapper;


import org.springframework.stereotype.Component;
import stock1337.stock1337.dto.stockHistoryDTO;
import stock1337.stock1337.enums.HistoryType;
import stock1337.stock1337.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockHistoryMapper {

    public stockHistoryDTO toDTO(stockHistory history) {
        if (history == null) return null;

        Article article = history.getArticle();
        Stock articleStock = article != null ? article.getStock() : null;
        Stock directStock = history.getStock();
        Stock effectiveStock = directStock != null ? directStock : articleStock;
        Departement departement = effectiveStock != null ? effectiveStock.getDepartement() : null;

        User user = history.getUser();


        String userDisplayName = "Système";
        boolean userExists = false;

        if (user != null) {
            userExists = true;
            String name = user.getName();
            String email = user.getEmail();

            if (name != null && !name.trim().isEmpty()) {
                userDisplayName = name;
            } else if (email != null && !email.trim().isEmpty()) {

                int atIndex = email.indexOf('@');
                if (atIndex > 0) {
                    userDisplayName = email.substring(0, atIndex);
                } else {
                    userDisplayName = email;
                }
            }
        }

        return stockHistoryDTO.builder()
                .id(history.getId())
                .articleId(article != null ? article.getId() : null)
                .articleName(article != null ? article.getName() : "Article supprimé")
                .stockName(effectiveStock != null ? effectiveStock.getName() : "Stock non spécifié")
                .departementName(departement != null ? departement.getName() : "Département non spécifié")
                .userName(userDisplayName)
                .quantityChange(history.getQuantityChange() != null ? history.getQuantityChange() : 0)
                .type(history.getType() != null ? history.getType() : HistoryType.AJUSTEMENT)
                .reason(history.getReason() != null ? history.getReason() : "Non spécifié")
                .recordedAt(history.getRecordedAt())
                .hasUser(userExists)
                .hasStock(effectiveStock != null)
                .hasDepartement(departement != null)
                .build();
    }
}

