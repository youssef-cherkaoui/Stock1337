package stock1337.stock1337.model;


import jakarta.persistence.*;
import lombok.*;
import stock1337.stock1337.enums.AlertType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    private Integer quantityAlert;

    private LocalDateTime sentAt;

    private String emailTo;

    private boolean emailSent;
}
