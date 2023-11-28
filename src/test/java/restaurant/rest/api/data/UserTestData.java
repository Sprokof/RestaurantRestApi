package restaurant.rest.api.data;

import restaurant.rest.api.matcher.MatcherFactory;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;

import java.time.LocalDateTime;
import java.util.Collections;


public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("registered", "roles");

    public static final int CREATED_USER_ID = 5;
    public static final int USER_ID = 1, ADMIN_ID = 3;
    public static final int NOT_FOUND = 0;
    public static final User USER_1 = new User("userName1", "user@email1.com", "userPassword1", Collections.singleton(Role.USER));
    public static final User USER_2 = new User("userName2", "user@email2.com", "userPassword2", Collections.singleton(Role.USER));
    public static final User ADMIN = new User("adminName", "admin@email.com", "userPassword", Collections.singleton(Role.ADMIN));

    static {
        USER_1.setId(USER_ID);
        USER_2.setId(USER_ID + 1);
        ADMIN.setId(ADMIN_ID);
    }

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
