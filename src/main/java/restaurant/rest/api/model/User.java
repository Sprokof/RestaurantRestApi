package restaurant.rest.api.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {

    @Setter
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Setter
    @Email
    @Column(name = "email", nullable = false, unique = true)
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
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @Column(name = "role")
    @BatchSize(size = 200)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Set<Role> roles;


    public void addRole(Role role){
        if(this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }


    public User(){
        this.registered = LocalDateTime.now();
    }

    public User(String name, String email, String password, Set<Role> roles){
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = LocalDateTime.now();
        this.roles = roles;
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
