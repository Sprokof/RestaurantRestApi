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

    public Vote getActualWithRestaurantByUserId(int userId) {
        return this.repository.getWithRestaurantByUserIdAndDate(LocalDate.now(), userId);
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

    public Vote getByUserIdAndDate(LocalDate localDate, int userId){
        return this.repository.getByUserIdAndDate(localDate, userId);
    }

    public int getVotesCount(LocalDate date, int restaurantId){
        return this.repository.getVotesCount(date, restaurantId);
    }

    public int getActualVotesCount(int restaurantId){
        return this.repository.getVotesCount(LocalDate.now(), restaurantId);
    }

}
