package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:u_id AND v.restaurant.id=:r_id")
    int delete(@Param("id") int id, @Param("u_id") int userId, @Param("r_id") int restaurantId);
    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.user.id=:u_id AND v.restaurant.id")
    Vote get(@Param("id") int id, @Param("u_id") int userId, @Param("r_id") int restaurantId);
    @Query("SELECT v FROM Vote v WHERE v.user.id=:u_id")
    List<Vote> getAllByUserId(@Param("u_id") int userId);
    @Query("SELECT v FROM Vote v WHERE v.acutal is true AND v.restuarant.id=:r_id")
    List<Vote> getAllByRestaurantId(@Param("r_id")int restaurantId);

    @Query("SELECT v FROM Vote v ORDER BY v.voteDate DESC")
    List<Vote> getAll();
}
