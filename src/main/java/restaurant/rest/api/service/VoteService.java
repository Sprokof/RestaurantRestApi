package restaurant.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import restaurant.rest.api.model.User;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.*;

@Service
public class VoteService {

    @Autowired
    private VoteRepository repository;

    public Vote create(Vote vote, int userId, int restaurantId){
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote, userId, restaurantId);
    }

    public void update(Vote vote, int userId, int restaurantId){
        Assert.notNull(vote, "vote must not be null");
        checkNotFound(repository.save(vote, userId, restaurantId),
                "userId=" + userId + ", restaurantId" + restaurantId);
    }

    public void delete(int id, int userId, int restaurantId){
        checkNotFound(repository.delete(id, userId, restaurantId),
                "id=" + id  + ", userId=" + userId +
                        ", restaurantId" + restaurantId);
    }

    public Vote get(int id, int userId, int restaurantId) {
        return checkNotFound(repository.get(id, userId, restaurantId),
                "id=" + id + ", userId=" + userId +
                        ", restaurantId" + restaurantId);
    }

    public Vote getActualByUserId(int userId) {
        return this.repository.getActualByUserId(userId);
    }


    public List<Vote> getAllWithRestaurantByUserId(int userId) {
        return this.repository.getAllWithRestaurantByUserId(userId);
    }

    public List<Vote> getAllWithUserByRestaurantId(int restaurantId) {
        return this.repository.getAllWithUserByRestaurantId(restaurantId);
    }

    public List<Vote> getAllActualWithUserByRestaurantId(int restaurantId) {
        return this.repository.getAllActualWithUserByRestaurantId(restaurantId);
    }

    public Vote getByLocalDate(LocalDate localDate, int userId){
        return this.repository.getByLocalDate(localDate, userId);
    }

    public void updateAll(boolean actual, int restaurantId){
        this.repository.updateAll(actual, restaurantId);
    }
}
