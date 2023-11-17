package restaurant.rest.api.repository;

import restaurant.rest.api.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant, int id);
    boolean delete(int id);
    Restaurant get(int id);
    Restaurant getWithMenus(int id);
    Restaurant getWithVotes(int id);
    List<Restaurant> getAll();
    List<Restaurant> getAllWithMenu();
    List<Restaurant> getAllWithMenuByDate(LocalDate date);


}
