package stock1337.stock1337.dto;


import lombok.Builder;
import lombok.Data;
import stock1337.stock1337.enums.HistoryType;

import java.time.LocalDateTime;

@Data
@Builder
public class stockHistoryDTO {
    private Long id;
    private Long articleId;
    private String articleName;
    private String stockName;
    private String userName;

    @Builder.Default
    private Integer quantityChange = 0;

    @Builder.Default
    private HistoryType type = HistoryType.AJUSTEMENT;

    @Builder.Default
    private String reason = "Non spécifié";

    private LocalDateTime recordedAt;

    private boolean hasUser;
    private boolean hasStock;

}
