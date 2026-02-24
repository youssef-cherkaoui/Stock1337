package stock1337.stock1337.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {

    private String name;
    private String description;
    private int quantity;
    private int minThreshold = 5;
    private Long stockId;
    private Long departementId;
}
