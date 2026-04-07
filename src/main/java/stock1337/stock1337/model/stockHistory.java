package stock1337.stock1337.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import stock1337.stock1337.enums.HistoryType;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class stockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer quantityChange;


    @Enumerated(EnumType.STRING)
    private HistoryType type;

    private String reason;

    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() {
        recordedAt = LocalDateTime.now();
    }
}
