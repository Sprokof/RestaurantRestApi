package restaurant.rest.api;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;
import restaurant.rest.api.to.AbstractBaseTo;
import restaurant.rest.api.to.UserTo;
import restaurant.rest.api.util.SecurityUtil;
import restaurant.rest.api.util.UserUtil;

import java.util.*;

@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, SecurityUtil.authorities(user.getRoles()));
        this.userTo = UserUtil.toDto(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
        this.userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }

}
