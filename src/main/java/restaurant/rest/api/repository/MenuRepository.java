package restaurant.rest.api.repository;

import restaurant.rest.api.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu, int restaurantId);
    boolean delete(int id, int restaurantId);
    Menu get(int id, int restaurantId);
    Menu getMenuByDateAndRestaurantId(LocalDate date, int restaurantId);
    List<Menu> getAll(int restaurantId);


}
