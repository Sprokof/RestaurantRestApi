package restaurant.rest.api;

import lombok.Getter;
import restaurant.rest.api.model.User;
import restaurant.rest.api.to.UserTo;
import restaurant.rest.api.util.UserUtil;



@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.toDto(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
        this.userTo = newTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }

}
