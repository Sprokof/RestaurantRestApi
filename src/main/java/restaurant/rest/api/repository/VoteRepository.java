package restaurant.rest.api.repository;

import restaurant.rest.api.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote, int userId, int restaurantId);
    Vote get(int id, int userId);
    List<Vote> getAll(int userId);
    boolean delete(int id, int userId);
    Vote getWithRestaurant(int id, int userId);
    Vote getWithRestaurantByUserIdAndDate(LocalDate date, int userId);
    Vote getByUserIdAndDate(LocalDate localDate, int userId);
    List<Vote> getAllWithRestaurantByUserId(int userId);
    List<Vote> getAllWithUserByRestaurantId(int restaurantId);
    List<Vote> getWithUserByRestaurantIdAndDate(LocalDate date, int restaurantId);
    int getVotesCount(LocalDate date, int restaurantId);


}
