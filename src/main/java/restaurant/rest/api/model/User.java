package restaurant.rest.api.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    @Setter
    private LocalDateTime registered;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Setter
    private Set<Role> roles;

    public void addRole(Role role){
        if(this.roles == null) this.roles = new HashSet<>();
        this.roles.add(role);
    }


    public User(){
        this.registered = LocalDateTime.now();
    }

    public User(String name, String email, String password, Role role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = LocalDateTime.now();
        addRole(role);
    }


    public User(String name, String email, String password, LocalDateTime registered, Set<Role> roles){
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.roles = roles;
    }


    public User(User user){
        this(user.name, user.email, user.password, user.registered, user.roles);
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
