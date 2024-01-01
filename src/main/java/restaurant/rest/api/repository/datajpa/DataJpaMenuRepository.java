package restaurant.rest.api.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DataJpaMenuRepository implements MenuRepository {
    private final CrudMenuRepository menuRepository;

    public DataJpaMenuRepository(CrudMenuRepository menuRepository, CrudRestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        menu.setRestaurantId(restaurantId);
        if (menu.isNew()) {
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
    public Menu getByDate(LocalDate date, int restaurantId) {
        return this.menuRepository.getByDate(date, restaurantId);
    }

    @Override
    public Menu getLast(int restaurantId) {
        return this.menuRepository.getLast(restaurantId);
    }

    @Override
    public boolean exist(LocalDate date, int restaurantId) {
        return this.menuRepository.exist(date, restaurantId) != 0;
    }
}

