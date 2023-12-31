package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
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

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE (m is null OR m.id = (SELECT max(m.id) FROM Menu m WHERE m.restaurantId=:id)) AND r.id=:id")
    Restaurant getWithMenu(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE m.date=:date ORDER BY r.name DESC")
    List<Restaurant> getAllWithMenuByDate(@Param("date") LocalDate date);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name% ORDER BY r.name DESC")
    List<Restaurant> getAllByName(@Param("name") String name);
    @Query("SELECT COUNT(v) FROM Restaurant r, Vote v WHERE r.id=v.restaurant.id AND v.voteDate=:date AND r.id=:id")
    int getVotesCount(@Param("id") int id, @Param("date") LocalDate date);

    @Query("SELECT r FROM Restaurant r, Vote v WHERE r.id=v.restaurant.id AND v.voteDate=:date GROUP BY r ORDER BY COUNT(v.id) DESC")
    List<Restaurant> getTopByDate(@Param("date") LocalDate date);
    @EntityGraph(attributePaths = "votes")
    @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
    Restaurant getWithVotes(@Param("id") int id);
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.votes v WHERE v.voteDate = (SELECT max(v.voteDate) FROM Vote v WHERE v.restaurant.id=:id) AND r.id=:id")
    Restaurant getWithLastVotes(@Param("id") int id);


}
