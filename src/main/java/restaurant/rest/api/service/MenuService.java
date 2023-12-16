package restaurant.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.*;


@Service
public class MenuService {

    @Autowired
    private MenuRepository repository;

    public Menu create(Menu menu, int restaurantId){
        Assert.notNull(menu, "menu must not be null");
        return this.repository.save(menu, restaurantId);
    }

    public void update(Menu menu, int restaurantId){
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(repository.save(menu, restaurantId), restaurantId);
    }

    public void delete(int id, int restaurantId){
        checkNotFound(repository.delete(id, restaurantId),
                "id=" + id + ", restaurantId=" + restaurantId);
    }

    public Menu get(int id, int restaurantId){
        return checkNotFound(repository.get(id, restaurantId),
                "id=" + id + ", restaurantId=" + restaurantId);
    }

    public List<Menu> getAll(int restaurantId){
        return this.repository.getAll(restaurantId);
    }

    public Menu getActualMenu(int restaurantId){
        return getMenuByDate(LocalDate.now(), restaurantId);
    }

    public Menu getMenuByDate(LocalDate date, int restaurantId){
        return this.repository.getMenuByDate(date, restaurantId);
    }
}
