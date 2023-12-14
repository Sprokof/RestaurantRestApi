package restaurant.rest.api.util;

import restaurant.rest.api.model.User;
import restaurant.rest.api.to.UserTo;

public class UserUtil {
    public static UserTo toDto(User user){
        UserTo userTo = new UserTo();
        userTo.setId(user.getId());
        userTo.setUsername(user.getUsername());
        userTo.setEmail(user.getEmail());
        userTo.setPassword(user.getPassword());
        userTo.setRegistered(user.getRegistered());
        userTo.setRoles(user.getRoles());
        return userTo;
    }
}
