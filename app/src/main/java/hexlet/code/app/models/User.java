package hexlet.code.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
