package restaurant.rest.api.repository;

import restaurant.rest.api.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    boolean delete(int id);
    Restaurant get(int id);
    Restaurant getWithMenu(int id);
    List<Restaurant> getAllByName(String name);
    List<Restaurant> getAllWithMenuByName(String name);
    List<Restaurant> getAll();
    List<Restaurant> getAllWithMenu();
    List<Restaurant> getAllWithMenuByDate(LocalDate date);
    boolean updateVotesCount(int restaurantId);
}
