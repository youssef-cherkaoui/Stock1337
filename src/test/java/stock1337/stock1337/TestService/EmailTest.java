package stock1337.stock1337.TestService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stock1337.stock1337.enums.AlertType;
import stock1337.stock1337.model.Article;
import stock1337.stock1337.service.EmailAlertService;

@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailAlertService emailAlertService;

    @Test
    void testSendAlert() {
        Article article = new Article();
        article.setId(1L);
        article.setName("Pc dell");
        article.setDescription("Pc dell");
        article.setQuantity(0);
        article.setMinThreshold(5);

        emailAlertService.sendStockAlert(article, AlertType.Empty);

        System.out.println("Email envoyé!");
    }
}
