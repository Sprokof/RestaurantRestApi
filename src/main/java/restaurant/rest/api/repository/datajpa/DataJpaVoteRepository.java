package restaurant.rest.api.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.User;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.repository.VoteRepository;

import java.util.List;

@Repository
@Transactional
public class DataJpaVoteRepository implements VoteRepository  {

    private final CrudVoteRepository voteRepository;

    private final CrudUserRepository userRepository;

    private final CrudRestaurantRepository restaurantRepository;

    @Autowired
    public DataJpaVoteRepository(CrudVoteRepository voteRepository, CrudUserRepository userRepository,
                                 CrudRestaurantRepository restaurantRepository){
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {
        User userRef = userRepository.getReferenceById(userId);
        Restaurant restaurantRef = restaurantRepository.getReferenceById(restaurantId);
        vote.setUser(userRef);
        vote.setRestaurant(restaurantRef);
        if(vote.isNew()){
            return this.voteRepository.save(vote);
        }
        return voteRepository.findById(vote.id())
                .filter(v -> v.getUser().id() == userId)
                .isPresent() ? voteRepository.save(vote) : null;
    }

    @Override
    public boolean delete(int id, int userId, int restaurantId) {
        return this.voteRepository.delete(id, userId, restaurantId) != 0;
    }

    @Override
    public Vote get(int id, int userId, int restaurantId) {
        return this.voteRepository.get(id, userId, restaurantId);
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
    public List<Vote> getAll() {
        return this.voteRepository.getAll();
    }
}