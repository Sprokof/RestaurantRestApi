package restaurant.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import restaurant.rest.api.config.ServiceTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;
import restaurant.rest.api.util.exception.NotFoundException;

import javax.xml.crypto.Data;
import java.util.List;

import static restaurant.rest.api.data.UserTestData.*;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfig.class)
public class UserServiceTest {

    @Autowired
    UserService service;

    @Test
    public void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    public void delete(){
        service.delete(CREATED_USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(CREATED_USER_ID));
    }


    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("admin@email.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
   public void duplicateEmailCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new User("name", "admin@email.com", "pass", Role.ADMIN)));

    }
    @Test
    public void duplicateNameCreate(){
        assertThrows(DataAccessException.class, () -> service.create(new User("userName1", "mail@email.com", "pass", Role.USER)));
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    public void getAll(){
        List<User> all = this.service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER_1, USER_2);
    }



}
