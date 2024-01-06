package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
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
    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.user.id=:u_id")
    Vote getByUserId(@Param("id") int id, @Param("u_id") int userId);
    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.restaurant.id=:r_id")
    Vote getByRestaurantId(@Param("id") int id, @Param("r_id") int restaurantId);
    @Query("SELECT v FROM Vote v WHERE v.user.id=:u_id")
    List<Vote> getAllByUserId(@Param("u_id") int userId);
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:r_id")
    List<Vote> getAllByRestaurantId(@Param("r_id") int restaurantId);
    @Query("SELECT v FROM Vote v WHERE v.voteDate = (SELECT max(v.voteDate) FROM Vote v WHERE v.restaurant.id=:r_id) AND v.restaurant.id=:r_id")
    List<Vote> getAllLastByRestaurantId(@Param("r_id") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.voteDate=:date AND v.user.id=:u_id")
    Vote getByDateAndUserId(@Param("date") LocalDate date, @Param("u_id") int userId);


}
