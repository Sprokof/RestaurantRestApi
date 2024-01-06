package restaurant.rest.api.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.User;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository voteRepository;

    private final CrudUserRepository userRepository;

    private final CrudRestaurantRepository restaurantRepository;

    public DataJpaVoteRepository(CrudVoteRepository voteRepository, CrudUserRepository userRepository,
                                 CrudRestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int userId, int restaurantId) {
        User userRef = userRepository.getReferenceById(userId);
        Restaurant restaurantRef = restaurantRepository.getReferenceById(restaurantId);
        vote.setUser(userRef);
        vote.setRestaurant(restaurantRef);
        if (vote.isNew()) {
            return this.voteRepository.save(vote);
        }
        return getByUserId(vote.id(), userId) != null ? voteRepository.save(vote) : null;
    }

    @Override
    public Vote getByUserId(int id, int userId) {
        return this.voteRepository.getByUserId(id, userId);
    }

    @Override
    public Vote getByRestaurantId(int id, int restaurantId) {
        return this.voteRepository.getByRestaurantId(id, restaurantId);
    }

    @Override
    public List<Vote> getAllByUserId(int userId) {
        return this.voteRepository.getAllByUserId(userId);
    }

    @Override
    public List<Vote> getAllByRestaurantId(int restaurantId) {
        return this.voteRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Vote> getAllLastByRestaurantId(int restaurantId) {
        return this.voteRepository.getAllLastByRestaurantId(restaurantId);
    }


    @Override
    public boolean delete(int id, int userId) {
        return this.voteRepository.delete(id, userId) != 0;
    }

    @Override
    public Vote getByDateAndUserId(LocalDate date, int userId) {
        return this.voteRepository.getByDateAndUserId(date, userId);
    }
}

