package restaurant.rest.api.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.User;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);
    @EntityGraph(attributePaths = "roles")
    @Query("SELECT u FROM User u WHERE u.email=:email")
    User getByEmail(@Param("email") String email);
    @EntityGraph(attributePaths = "roles")
    @Query("SELECT u FROM User u WHERE u.username=:username")
    User getByUsername(@Param("username") String username);




}
