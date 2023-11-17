package restaurant.rest.api.repository;

import restaurant.rest.api.model.MenuItem;

import java.util.Set;

public interface MenuItemRepository {
    MenuItem save(MenuItem item, int menuId);
    boolean delete(int id, int menuId);
    Set<MenuItem> getAll(int menuId);
    MenuItem get(int id, int menuId);
}
