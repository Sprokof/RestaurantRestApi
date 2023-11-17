package restaurant.rest.api.repository.datajpa;

import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Menu;

import java.util.List;
@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:r_id")
    int delete(@Param("id") int id, @Param("r_id") int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.id=:id AND m.restaurant.id=:r_id")
    Menu get(@Param("id") int id, @Param("r_id") int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:r_id")
    List<Menu> getAll(@Param("r_id") int restaurantId);
}
