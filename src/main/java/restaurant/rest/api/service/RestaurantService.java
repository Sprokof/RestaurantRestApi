package restaurant.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.*;


@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    public Restaurant create(Restaurant restaurant){
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(Restaurant restaurant){
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public Restaurant get(int id){
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void delete(int id){
        checkNotFoundWithId(repository.delete(id), id);
    }

    public List<Restaurant> getAll(){
        return this.repository.getAll();
    }

    public List<Restaurant> getAllWithMenu(){
        return repository.getAllWithMenu();
    }
    public List<Restaurant> getAllWithMenuByDate(LocalDate date){
        return this.repository.getAllWithMenuByDate(date);
    }
    public Restaurant getWithMaxVotesByDate(LocalDate date){
        return repository.getWithMaxVotesByDate(date);
    }
    public boolean updateVotesCount(int restaurantId){
        return this.repository.updateVotesCount(restaurantId);
    }

    public Restaurant getWithMenus(int id){
        return checkNotFoundWithId(this.repository.getWithMenus(id), id);
    }
}
