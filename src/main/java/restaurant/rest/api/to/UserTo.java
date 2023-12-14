package restaurant.rest.api.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserTo extends AbstractBaseTo {
    private String username;
    private String email;
    private String password;
    private LocalDateTime registered;
    private Set<Role> roles;

    @Override
    public User toEntity() {
        User user = new User();
        user.setId(this.getId());
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setRoles(this.roles);
        user.setRegistered(this.registered);
        return user;
    }





}
