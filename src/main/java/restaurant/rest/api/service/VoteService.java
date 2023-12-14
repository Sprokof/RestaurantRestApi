package restaurant.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

    public void delete(int id, int userId){
        checkNotFound(repository.delete(id, userId),
                "id=" + id  + ", userId=" + userId + ", restaurantId");
    }

    public Vote getWithRestaurant(int id, int userId){
        return checkNotFound(repository.getWithRestaurant(id, userId),
                "id=" + id  + ", userId" + userId);
    }

    public Vote getActualWithRestaurantByUserId(int userId) {
        return this.repository.getWithRestaurantByUserIdAndDate(LocalDate.now(), userId);
    }

    public Vote get(int id, int userId) {
        return this.repository.get(id, userId);
    }

    public List<Vote> getAllWithRestaurantByUserId(int userId) {
        return this.repository.getAllWithRestaurantByUserId(userId);
    }

    public List<Vote> getAllWithUserByRestaurantId(int restaurantId) {
        return this.repository.getAllWithUserByRestaurantId(restaurantId);
    }

    public List<Vote> getAllActualWithUserByRestaurantId(int restaurantId) {
        return this.repository.getWithUserByRestaurantIdAndDate(LocalDate.now(), restaurantId);
    }

    public List<Vote> getAll(int userId) {
        return this.repository.getAll(userId);
    }

    public Vote getActual(int userId){
        return this.repository.getByUserIdAndDate(LocalDate.now(), userId);
    }

    public int getVotesCount(LocalDate date, int restaurantId){
        return this.repository.getVotesCount(date, restaurantId);
    }

    public int getActualVotesCount(int restaurantId){
        return this.repository.getVotesCount(LocalDate.now(), restaurantId);
    }

}
