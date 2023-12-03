package restaurant.rest.api.repository.datajpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.repository.MenuItemRepository;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class DataJpaMenuItemRepository implements MenuItemRepository {
    private final CrudMenuItemRepository itemRepository;
    public DataJpaMenuItemRepository(CrudMenuItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public MenuItem save(MenuItem item, int menuId) {
        item.setMenuId(menuId);
        if (item.isNew()) {
            return itemRepository.save(item);
        }
        return get(item.id(), menuId) != null ? itemRepository.save(item) : null;
    }


    @Override
    public boolean delete(int id, int menuId) {
        return this.itemRepository.delete(id, menuId) != 0;
    }

    @Override
    public Set<MenuItem> getAll(int menuId) {
        return itemRepository.getAll(menuId);
    }

    @Override
    public MenuItem get(int id, int menuId) {
        return itemRepository.get(id, menuId);
    }
}
