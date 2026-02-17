package Model;


import Enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
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
