package restaurant.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.repository.MenuItemRepository;

import java.util.Set;

import static restaurant.rest.api.util.ValidationUtil.*;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository repository;

    public MenuItem create(MenuItem menuItem, int menuId){
        Assert.notNull(menuItem, "menuItem must not be null");
        return this.repository.save(menuItem, menuId);
    }

    public void update(MenuItem menuItem, int menuId){
        Assert.notNull(menuItem, "menuItem must not be null");
        checkNotFoundWithId(repository.save(menuItem, menuId), menuId);
    }

    public MenuItem get(int id, int menuId){
        return checkNotFound(repository.get(id, menuId), "id=" + id + ", menuId" + menuId);
    }

    public void delete(int id, int menuId){
        checkNotFound(repository.delete(id, menuId), "id=" + id + ", menuId" + menuId);
    }

    public Set<MenuItem> getAll(int menuId){
        return this.repository.getAll(menuId);
    }
}
