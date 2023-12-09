package restaurant.rest.api;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;
import restaurant.rest.api.to.UserTo;

import java.util.*;

@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private final UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, authorities(user.getRoles()));
        this.userTo = (UserTo) new UserTo().toDto(user);
    }


    private static Set<GrantedAuthority> authorities(Set<Role> roles){
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole())));
        return authorities;
    }
}
