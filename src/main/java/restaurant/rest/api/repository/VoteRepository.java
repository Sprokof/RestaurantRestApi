package restaurant.rest.api.repository;

import restaurant.rest.api.model.Vote;

import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote, int userId, int restaurantId);
    boolean delete(int id, int userId, int restaurantId);
    Vote get(int id, int userId, int restaurantId);
    List<Vote> getAll();
    List<Vote> getAllByUserId(int userId);
    List<Vote> getAllByRestaurantId(int restaurantId);
}
