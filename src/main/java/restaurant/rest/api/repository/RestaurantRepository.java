package restaurant.rest.api.repository;

import org.springframework.data.repository.query.Param;
import restaurant.rest.api.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    boolean delete(int id);
    Restaurant get(int id);
    List<Restaurant> getAllByName(String name);
    List<Restaurant> getAllWithMenuByNameAndDate(LocalDate date, String name);
    List<Restaurant> getAll();
    List<Restaurant> getAllWithMenuByDate(LocalDate date);
    Restaurant getWithMenuByDate(LocalDate date, int id);
}
