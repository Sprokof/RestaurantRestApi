package restaurant.rest.api.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {

    @Setter
    @Column(name = "name", nullable = false, unique = true)
    private String username;

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

    @Column(name = "enabled")
    @Setter
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Setter
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @Column(name = "role")
    @BatchSize(size = 200)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.EAGER)
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

    public User(String userName, String email, String password, Set<Role> roles){
        this.username = userName;
        this.email = email;
        this.password = password;
        this.registered = LocalDateTime.now();
        this.roles = roles;
        this.enabled = true;
    }


    public User(String username, String email, String password, LocalDateTime registered, Set<Role> roles){
        this.username = username;
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.roles = roles;
        this.enabled = true;
    }


    public User(User user){
        this(user.username, user.email, user.password, user.registered, user.roles);
        this.setEnabled(true);
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registered=" + registered +
                '}';
    }


}
