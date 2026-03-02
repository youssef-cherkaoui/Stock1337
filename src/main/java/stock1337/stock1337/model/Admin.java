package stock1337.stock1337.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("admin")
public class Admin extends Person {

    public Admin(){

    }
}
