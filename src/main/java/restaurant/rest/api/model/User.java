package restaurant.rest.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {

    @Setter
    @Column(name = "user_name", nullable = false)
    private String name;

    @Setter
    @Email
    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "user_password")
    private String password;

    @Column(name = "registered")
    private final LocalDateTime registered;


    public User(){
        this.registered = LocalDateTime.now();
    }

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = LocalDateTime.now();
    }

}
