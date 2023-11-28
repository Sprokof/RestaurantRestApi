package restaurant.rest.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.rest.api.config.ServiceTestConfig;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;

import static restaurant.rest.api.data.RestaurantTestData.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfig.class)
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void create(){
        Restaurant created = service.create(getNew());
        int createdId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(createdId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(createdId), newRestaurant);
    }

    @Test
    public void delete(){
        service.delete(CREATED_RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.get(CREATED_RESTAURANT_ID));
    }

    @Test
    public void getAllByName(){
        List<Restaurant> all = service.getAllByName("restaurant");
        RESTAURANT_MATCHER.assertMatch(all, RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void getAllWithMenuByDate(){
        List<Restaurant> all = service.getAllWithMenuByDate(LocalDate.now());
        RESTAURANT_MATCHER.assertMatch(all, RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);

    }

    @Test
    public void update(){
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID), updated);
    }



    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }


}
