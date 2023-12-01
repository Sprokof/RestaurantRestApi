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

    @CacheEvict(value = "restaurants", allEntries = true)
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
    @Cacheable("restaurants")
    public List<Restaurant> getAll(){
        return this.repository.getAll();
    }
    public List<Restaurant> getAllWithMenu(){
        return repository.getAllWithMenu();
    }
    public List<Restaurant> getAllWithMenuByDate(LocalDate date){
        return this.repository.getAllWithMenuByDate(date);
    }
    public Restaurant getWithMenus(int id){
        return checkNotFoundWithId(this.repository.getWithMenu(id), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllByName(String name){
        return this.repository.getAllByName(name);
    }

    public List<Restaurant> getAllWithMenuByName(String name){
        return this.repository.getAllWithMenuByName(name);
    }




}
