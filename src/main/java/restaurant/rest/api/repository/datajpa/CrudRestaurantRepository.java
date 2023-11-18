package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.User;
import restaurant.rest.api.model.Vote;

import java.time.LocalDate;
import java.util.List;
@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE r.id=:id AND m.menus.actual is true")
    Restaurant getWithMenus(int id);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE m.actual is true")
    List<Restaurant> getAllWithMenu();

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.votes m WHERE r.id=:id AND m.votes.actual is true")
    Restaurant getWithVotes(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE m.date=:date")
    List<Restaurant> getAllWithMenuByDate(@Param("date") LocalDate date);

    @Query("SELECT r FROM Restautant r, Vote v WHERE r.id=v.restaurant.id " +
            "AND v.voteDate=:date AND r.countVotes = (SELECT MAX(r.countVotes) FROM Restaurant r)")
    Restaurant getWithMaxVotesByDate(@Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE r Restaurant SET r.countVotes = (SELECT v.countVotes FROM Vote v WHERE v.restaurant.id=:r_id AND v.actual is true)")
    int updateVotesCount(int restaurantId);

}
