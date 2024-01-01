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
                "userId=" + userId + ", restaurantId=" + restaurantId);
    }

    public void delete(int id, int userId){
        checkNotFound(repository.delete(id, userId),
                "id=" + id  + ", userId=" + userId);
    }
    public Vote getByRestaurantId(int id, int restaurantId){
        return checkNotFound(this.repository.getByUserId(id, restaurantId),
                "id=" + id  + ", restaurantId=" + restaurantId);
    }
    public Vote getByUserId(int id, int userId){
        return checkNotFound(this.repository.getByUserId(id, userId),
                "id=" + id  + ", userId=" + userId);

    }
    public List<Vote> getAllByUserId(int userId){
        return this.repository.getAllByUserId(userId);
    }
    public List<Vote> getAllByRestaurantId(int restaurantId){
        return this.repository.getAllByRestaurantId(restaurantId);
    }
    public List<Vote> getAllLastByRestaurantId(int restaurantId){
        return this.repository.getAllLastByRestaurantId(restaurantId);
    }
}
