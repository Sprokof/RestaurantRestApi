package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.MenuItem;

import java.util.Set;

@Transactional(readOnly = true)
public interface CrudMenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuItem mi WHERE mi.id=:id AND m.menu.id=:m_id")
    int delete(@Param("id") int id, @Param("m_id") int menuId);

    @Query("SELECT mi FROM Menu mi WHERE mi.id=:id AND mi.menu.id=:m_id")
    MenuItem get(@Param("id") int id, @Param("m_id") int menuId);

    @Query("SELECT mi FROM Menu mi WHERE mi.menu.id=:m_id")
    Set<MenuItem> getAll(int menuId);


}
