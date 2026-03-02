package stock1337.stock1337.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity

@DiscriminatorValue("user")
public class User extends Person{

    public User() {
    }
}
