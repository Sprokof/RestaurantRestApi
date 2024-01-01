package restaurant.rest.api.repository;

import restaurant.rest.api.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote, int userId, int restaurantId);
    Vote getByUserId(int id, int userId);
    Vote getByRestaurantId(int id, int restaurantId);
    List<Vote> getAllByUserId(int userId);
    List<Vote> getAllByRestaurantId(int restaurantId);
    List<Vote> getAllLastByRestaurantId(int restaurantId);
    boolean delete(int id, int userId);



}
