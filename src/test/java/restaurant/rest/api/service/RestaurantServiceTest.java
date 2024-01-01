package restaurant.rest.api.service;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import restaurant.rest.api.config.ServiceTestConfig;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static restaurant.rest.api.data.RestaurantTestData.*;

@SpringBootTest(classes = ServiceTestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create(){
        Restaurant created = service.create(getNew());
        int createdId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(createdId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(createdId), newRestaurant);
    }

    @Test
    void delete(){
        service.delete(CREATED_RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.get(CREATED_RESTAURANT_ID));
    }

    @Test
    void getAllByName(){
        List<Restaurant> all = service.getAllByName("restaurant");
        RESTAURANT_MATCHER.assertMatch(all, RESTAURANT_4, RESTAURANT_3, RESTAURANT_2, RESTAURANT_1);
    }

    @Test
    void getAllWithMenuByDate(){
        List<Restaurant> all = service.getAllWithMenuByDate(LocalDate.now());
        RESTAURANT_MATCHER.assertMatch(all, RESTAURANT_3, RESTAURANT_2, RESTAURANT_1);

    }

    @Test
    @Order(2)
    void getWithMenu() {
        Restaurant found = this.service.getWithMenu(RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(found, RESTAURANT_1);
    }


    @Test
    void update(){
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID), updated);
    }
    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void getVotesCount(){
        int actual = this.service.getVotesCount(RESTAURANT_ID + 1);
        assertEquals(2, actual);
    }

    @Test
    @Order(1)
    void getTopsByDate(){
        List<Restaurant> top = this.service.getTopByDate(LocalDate.now(), TOP);
        RESTAURANT_MATCHER.assertMatch(top, RESTAURANT_2, RESTAURANT_1);
    }
}
