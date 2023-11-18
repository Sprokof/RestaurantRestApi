package restaurant.rest.api.repository.datajpa;

import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.repository.MenuItemRepository;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Transactional(readOnly = true)
public class DataJpaMenuItemRepository implements MenuItemRepository {
    private final CrudMenuItemRepository itemRepository;
    private final CrudMenuRepository menuRepository;

    public DataJpaMenuItemRepository(CrudMenuItemRepository itemRepository, CrudMenuRepository menuRepository){
        this.itemRepository = itemRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    @Transactional
    public MenuItem save(MenuItem item, int menuId) {
        Menu ref = menuRepository.getReferenceById(menuId);
        item.setMenu(ref);
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
