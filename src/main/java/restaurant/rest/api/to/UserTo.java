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
    private Integer id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime registered;
    private Set<Role> roles;

    @Override
    protected AbstractBaseEntity toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setRoles(this.roles);
        user.setRegistered(this.registered);
        return user;
    }

    @Override
    protected AbstractBaseTo toDto(AbstractBaseEntity entity) {
        User user = (User) entity;
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setRegistered(user.getRegistered());
        this.setRoles(user.getRoles());
        return this;
    }


}
