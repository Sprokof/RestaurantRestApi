package restaurant.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.repository.RestaurantRepository;
import restaurant.rest.api.to.RestaurantTo;
import restaurant.rest.api.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static restaurant.rest.api.util.ValidationUtil.*;


@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return this.repository.getAll();
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllWithMenuByDate(LocalDate date) {
        return this.repository.getAllWithMenuByDate(date);
    }

    public Restaurant getWithMenu(int id) {
        return checkNotFoundWithId(this.repository.getWithMenu(id), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllByName(String name) {
        return this.repository.getAllByName(name);
    }

    public int getVotesCount(int id) {
        return this.repository.getVotesCount(id, LocalDate.now());
    }

    public List<Restaurant> getTopByDate(LocalDate date, int top) {
        List<Restaurant> restaurants = this.repository.getTopByDate(date);
        return restaurants.subList(0, Math.min(restaurants.size(), top));
    }

    public List<Restaurant> getActualTop(int top) {
        return getTopByDate(LocalDate.now(), top);
    }

    public Restaurant getWithVotes(int id){
        return checkNotFoundWithId(this.repository.getWithVotes(id), id);
    }
    public Restaurant getWithLastVotes(int id){
        return checkNotFoundWithId(this.repository.getWithLastVotes(id), id);

    }

}