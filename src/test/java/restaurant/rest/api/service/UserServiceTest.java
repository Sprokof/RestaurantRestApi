package restaurant.rest.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import restaurant.rest.api.config.ServiceTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;
import restaurant.rest.api.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static restaurant.rest.api.data.UserTestData.*;

@SpringBootTest(classes = ServiceTestConfig.class)
public class UserServiceTest {

    @Autowired
    UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void delete(){
        service.delete(CREATED_USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(CREATED_USER_ID));
    }


    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("admin@email.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void duplicateEmailCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new User("name", "admin@email.com", "pass", Collections.singleton(Role.ADMIN))));

    }
    @Test
    void duplicateNameCreate(){
        assertThrows(DataAccessException.class, () -> service.create(new User("userName1", "mail@email.com", "pass", Collections.singleton(Role.USER))));
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    void getAll(){
        List<User> all = this.service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER_1, USER_2);
    }



}
