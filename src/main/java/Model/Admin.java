package Model;


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
