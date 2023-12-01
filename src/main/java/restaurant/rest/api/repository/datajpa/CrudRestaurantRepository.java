package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE r.id=:id AND m.date = NULLIF(:date, null)")
    Restaurant getWithMenuByDate(@Param("date") LocalDate date, @Param("id") int id);
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE m.date=:date ORDER BY r.countVotes DESC")
    List<Restaurant> getAllWithMenuByDate(@Param("date") LocalDate date);
    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name% ORDER BY r.countVotes DESC")
    List<Restaurant> getAllByName(@Param("name") String name);
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE m.date = NULLIF(:date, null) AND r.name LIKE %:name% ORDER BY r.countVotes DESC")
    List<Restaurant> getAllWithMenuByNameAndDate(@Param("date") LocalDate date, @Param("name") String name);



}
