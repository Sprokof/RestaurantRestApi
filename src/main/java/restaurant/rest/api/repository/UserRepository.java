package restaurant.rest.api.repository;

import org.springframework.data.jpa.repository.Query;
import restaurant.rest.api.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {
    User save(User user);
    User get(int id);
    boolean delete(int id);
    List<User> getAll();
    User getByEmail(String email);
    User getByUsername(String username);
    User getWithVotes(int id);
    User getWithLastVote(int id);

}
