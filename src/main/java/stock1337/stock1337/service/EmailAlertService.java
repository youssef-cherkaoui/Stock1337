package stock1337.stock1337.service;

import stock1337.stock1337.enums.AlertType;
import stock1337.stock1337.model.Article;

public interface EmailAlertService {

    void sendStockAlert(Article article, AlertType alertType);

    void checkAndSendAlert(Long articleId);
}
