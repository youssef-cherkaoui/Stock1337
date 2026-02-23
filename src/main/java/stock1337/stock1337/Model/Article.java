package stock1337.stock1337.Model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private int minThreshold = 5;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
}
