package restaurant.rest.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.rest.api.config.ServiceTestConfig;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static restaurant.rest.api.data.MenuTestData.*;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_ID;

@SpringBootTest(classes = ServiceTestConfig.class)
public class MenuServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void create(){
        Menu created = service.create(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(service.get(newId, RESTAURANT_ID), newMenu);

    }

    @Test
    public void delete(){
        service.delete(CREATED_MENU_ID, RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.get(CREATED_MENU_ID, RESTAURANT_ID));
    }

    @Test
    public void getAll(){
        List<Menu> all = service.getAll(RESTAURANT_ID);
        MENU_MATCHER.assertMatch(all, List.of(MENU_1));
    }


    @Test
    public void get() {
        Menu menu = service.get(MENU_ID, RESTAURANT_ID);
        MENU_MATCHER.assertMatch(menu, MENU_1);
    }


    @Test
    public void getNotFound(){
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_ID));
    }

    public void deleteNotFound(){
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT_ID));

    }
    @Test
    public void getActualMenu(){
        Menu actual = service.getActualMenu(RESTAURANT_ID  + 1);
        MENU_MATCHER.assertMatch(actual, MENU_2);
    }

}
