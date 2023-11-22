package data;

import matcher.MatcherFactory;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;

import java.time.LocalDateTime;
import java.util.Collections;


public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("registered", "roles");

    public static final int USER_ID = 1;
    public static final int NOT_FOUND = 0;
    public static final User USER_1 = new User("userName1", "user@email1", "userPassword1", Role.USER);
    public static final User USER_2 = new User("userName2", "user@email2", "userPassword2", Role.USER);
    public static final User ADMIN = new User("adminName", "admin@email", "userPassword", Role.ADMIN);
    public static User getNew() {
        return new User("New", "new@gmail.com", "newPass", LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER_1);
        updated.setId(USER_ID);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }
}
