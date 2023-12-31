package restaurant.rest.api.data;

import restaurant.rest.api.matcher.MatcherFactory;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;
import restaurant.rest.api.to.UserTo;
import restaurant.rest.api.util.UserUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;


public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "roles", "votes");

    public static final MatcherFactory.Matcher<UserTo> USER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserTo.class, "registered", "roles", "votes");

    public static final int CREATED_USER_ID = 5;
    public static final int USER_ID = 1, USER_2_ID = 2, ADMIN_ID = 3;
    public static final String USER_NAME = "userName1", USER_NAME_2 = "userName2", ADMIN_NAME = "adminName";
    public static final int NOT_FOUND = 0;
    public static final User USER_1 = new User("userName1", "user@email1.com", "userPassword1", Collections.singleton(Role.USER));
    public static final User USER_2 = new User("userName2", "user@email2.com", "userPassword2", Collections.singleton(Role.USER));
    public static final User ADMIN = new User("adminName", "admin@email.com", "userPassword", Set.of(Role.ADMIN, Role.USER));

    static {
        USER_1.setId(USER_ID);
        USER_2.setId(USER_ID + 1);
        ADMIN.setId(ADMIN_ID);
    }

    public static User getNew() {
        return new User("New", "new@gmail.com", "newPass", LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static UserTo getNewTo() {
        User user =  new User("New", "new@gmail.com", "newPass", LocalDateTime.now(), Collections.singleton(Role.USER));
        return UserUtil.toDto(user);
    }

    public static User getUpdated() {
        User updated = new User(USER_1);
        updated.setId(USER_ID);
        updated.setEmail("update@gmail.com");
        updated.setUsername("UpdatedName");
        updated.setPassword("updatedPass");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }

    public static UserTo getUpdatedTo() {
        User updated = new User(USER_1);
        updated.setId(USER_ID);
        updated.setEmail("update@gmail.com");
        updated.setUsername("UpdatedName");
        updated.setPassword("updatedPass");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return UserUtil.toDto(updated);
    }

}
