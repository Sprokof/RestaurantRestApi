package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:u_id")
    int delete(@Param("id") int id, @Param("u_id") int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.id=:id AND v.user.id=:u_id")
    Vote getWithRestaurant(@Param("id") int id, @Param("u_id") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:u_id ORDER BY v.voteDate DESC")
    List<Vote> getAll(@Param("u_id") int userId);

    @Query("SELECT v FROM Vote v WHERE v.voteDate=:date AND v.user.id=:u_id ")
    Vote getWithRestaurantByUserIdAndDate(@Param("date") LocalDate date, @Param("u_id") int userId);
    @Query("SELECT v FROM Vote v WHERE v.voteDate=:date AND v.user.id=:u_id")
    Vote getByUserIdAndDate(@Param("date") LocalDate localDate, @Param("u_id") int userId);
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:u_id")
    List<Vote> getAllWithRestaurantByUserId(@Param("u_id") int userId);
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.restaurant.id=:r_id")
    List<Vote> getAllWithUserByRestaurantId(@Param("r_id") int restaurantId);
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.voteDate=:date AND v.restaurant.id=:r_id")
    List<Vote> getWithUserByRestaurantIdByDate(@Param("date") LocalDate date, @Param("r_id") int restaurantId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.voteDate=:date AND v.restaurant.id=:r_id")
    int getVotesCount(@Param("date") LocalDate date, @Param("r_id") int restaurantId);


}
