package stock1337.stock1337.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(optional = true)
    @JoinColumn(name = "article_id", nullable = true)
    @JsonIgnoreProperties({"stock", "departement", "description", "quantity", "minThreshold", "createdAt", "updatedAt"})    private Article article;

    @ManyToOne(optional = true)
    @JoinColumn(name = "stock_id", nullable = true)
    @JsonIgnoreProperties({"departement", "localisation", "articles", "responsable"})
    private Stock stock;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIgnoreProperties({"email", "password", "role", "demandes", "createdAt", "updatedAt", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities"})
    private User user;

    private Integer quantityChange;


    @Enumerated(EnumType.STRING)
    private HistoryType type;

    private String reason;

    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() {
        if (recordedAt == null) {
            recordedAt = LocalDateTime.now();
        }
    }
}
