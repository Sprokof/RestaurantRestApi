package restaurant.rest.api.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class DataJpaRestaurantRepository implements RestaurantRepository  {

    private final CrudRestaurantRepository repository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return restaurant.isNew() ? repository.save(restaurant)
                : (get(restaurant.id()) != null ? repository.save(restaurant) : null);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return this.repository.findAll();
    }

    @Override
    public List<Restaurant> getAllWithMenuByDate(LocalDate date) {
        return this.repository.getAllWithMenuByDate(date);
    }

    @Override
    public List<Restaurant> getAllByName(String name) {
        return this.repository.getAllByName(name);
    }

    @Override
    public List<Restaurant> getAllWithMenuByNameAndDate(LocalDate date, String name) {
        return this.repository.getAllWithMenuByNameAndDate(date, name);
    }

    @Override
    public List<Restaurant> getAllWithMenu() {
        return this.getAllWithMenuByDate(LocalDate.now());
    }

    @Override
    public Restaurant getWithMenuByDate(LocalDate date, int id) {
        return this.repository.getWithMenuByDate(date, id);
    }
}
