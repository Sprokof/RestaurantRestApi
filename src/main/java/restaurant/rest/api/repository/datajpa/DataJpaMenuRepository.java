package restaurant.rest.api.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.repository.MenuRepository;

import java.util.List;

@Repository
public class DataJpaMenuRepository implements MenuRepository {
    private final CrudMenuRepository menuRepository;

    private final CrudRestaurantRepository restaurantRepository;

    public DataJpaMenuRepository(CrudMenuRepository menuRepository, CrudRestaurantRepository restaurantRepository){
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        Restaurant ref = restaurantRepository.getReferenceById(restaurantId);
        menu.setRestaurant(ref);
        if(menu.isNew()){
            return menuRepository.save(menu);
        }
        return get(menu.id(), restaurantId) != null ? menuRepository.save(menu) : null;
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return menuRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Menu get(int id, int restaurantId) {
        return this.menuRepository.get(id, restaurantId);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return this.menuRepository.getAll(restaurantId);
    }

    @Override
    public boolean updatePrevision(boolean actual, int restaurantId) {
        return this.menuRepository.updatePrevision(actual, restaurantId) != 0;
    }
}

