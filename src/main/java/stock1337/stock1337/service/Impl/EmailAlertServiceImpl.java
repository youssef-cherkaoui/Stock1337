package stock1337.stock1337.service.Impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import stock1337.stock1337.enums.AlertType;
import stock1337.stock1337.model.AlertHistory;
import stock1337.stock1337.model.Article;
import stock1337.stock1337.repository.AlertHistoryRepository;
import stock1337.stock1337.repository.ArticleRepository;
import stock1337.stock1337.service.EmailAlertService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailAlertServiceImpl implements EmailAlertService {


    private final JavaMailSender mailSender;
    private final ArticleRepository articleRepository;
    private final AlertHistoryRepository alertHistoryRepository;

    @Value("${alert.email.to:youssefessaddik.cherk@gmail.com}")
    private String alertEmailTo;

    @Value("${alert.email.from:noreply@stock1337.ma}")
    private String alertEmailFrom;

    @Value("${alert.cooldown.minutes:30}")
    private int alertCooldownMinutes;



    @Override
    @Transactional
    public void checkAndSendAlert(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé" + articleId));
        int qte = article.getQuantity();
        int minThreshold = article.getMinThreshold();

        if(qte == 0){
            sendAlertIfNotRecent(article, AlertType.Empty);
        } else if (qte <= 5 || qte <= minThreshold) {
            sendAlertIfNotRecent(article, AlertType.CRITICAL);
        }
    }

    private void sendAlertIfNotRecent(Article article, AlertType alertType) {

        LocalDateTime cooldownTime = LocalDateTime.now().minusMinutes(alertCooldownMinutes);

        boolean recentAlert = alertHistoryRepository.existsRecentAlert(
                article.getId(),
                alertType.toString(),
                cooldownTime
        );
        if(!recentAlert){
            sendStockAlert(article, alertType);
        }
    }

    @Override
    @Transactional
    public void sendStockAlert(Article article, AlertType alertType) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(alertEmailFrom);
            message.setTo(alertEmailTo.split(","));
            message.setSubject(buildSubject(article, alertType));
            message.setText(buildBody(article, alertType));

            mailSender.send(message);

            AlertHistory history = AlertHistory.builder()
                    .article(article)
                    .alertType(alertType)
                    .quantityAlert(article.getQuantity())
                    .sentAt(LocalDateTime.now())
                    .emailTo(alertEmailTo)
                    .emailSent(true)
                    .build();
            alertHistoryRepository.save(history);
            System.out.println("✅ Alert sent: " + alertType + " for " + article.getName());
        } catch (Exception e) {
            System.err.println("Failed" + e.getMessage());

            AlertHistory history = AlertHistory.builder()
                    .article(article)
                    .alertType(alertType)
                    .quantityAlert(article.getQuantity())
                    .sentAt(LocalDateTime.now())
                    .emailTo(alertEmailTo)
                    .emailSent(false)
                    .build();
            alertHistoryRepository.save(history);

        }
    }

    private String buildSubject(Article article, AlertType alertType) {
        String emoji = alertType == AlertType.Empty ? "🚨" : "⚠️";
        String code = alertType == AlertType.Empty ? "URGENT" : "ALERT";
        return String.format("%s [%s-%s] Stock %s - %s (ID:%d)",
                emoji, code, alertType, alertType.name(), article.getName(), article.getId());
    }

    private String buildBody(Article article, AlertType alertType) {
        String header = alertType == AlertType.Empty
                ? "🚨 RUPTURE DE STOCK DÉTECTÉE 🚨\n" + "=".repeat(40)
                : "⚠️ STOCK CRITIQUE ⚠️\n" + "=".repeat(40);

        String severity = alertType == AlertType.Empty
                ? "ACTION IMMÉDIATE REQUISE - Réapprovisionnement urgent!"
                : "Planifier réapprovisionnement prochainement.";

        return String.format("""
                %s
                
                Article: %s
                ID: #%d
                Description: %s
                
                📊 STOCK ACTUEL
                Quantité: %d unité(s)
                Seuil minimum: %d
                Département: %s
                Stock: %s
                
                🔔 RECOMMANDATION
                %s
                
                ---
                Système Stock1337 | %s
                """,
                header,
                article.getName(),
                article.getId(),
                article.getDescription() != null ? article.getDescription() : "N/A",
                article.getQuantity(),
                article.getMinThreshold(),
                article.getDepartement() != null ? article.getDepartement().getName() : "N/A",
                article.getStock() != null ? article.getStock().getName() : "N/A",
                severity,
                LocalDateTime.now()
        );
    }
}
