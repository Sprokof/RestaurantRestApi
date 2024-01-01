package restaurant.rest.api.repository;

import restaurant.rest.api.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    boolean delete(int id);
    Restaurant get(int id);
    List<Restaurant> getAllByName(String name);
    List<Restaurant> getAll();
    List<Restaurant> getAllWithMenuByDate(LocalDate date);
    Restaurant getWithMenu(int id);
    List<Restaurant> getTopByDate(LocalDate date);
    int getVotesCount(int id, LocalDate date);
    Restaurant getWithVotes(int id);
    Restaurant getWithLastVotes(int id);


}
