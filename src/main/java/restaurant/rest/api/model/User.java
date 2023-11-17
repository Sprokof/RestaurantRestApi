package restaurant.rest.api.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.REFRESH)
    private Set<Vote> votes;



    public User(){
        this.registered = LocalDateTime.now();
    }

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registered=" + registered +
                '}';
    }
}
