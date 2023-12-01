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

@Repository
@Transactional
public class DataJpaVoteRepository implements VoteRepository  {

    private final CrudVoteRepository voteRepository;

    private final CrudUserRepository userRepository;

    private final CrudRestaurantRepository restaurantRepository;

    public DataJpaVoteRepository(CrudVoteRepository voteRepository, CrudUserRepository userRepository,
                                 CrudRestaurantRepository restaurantRepository){
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
    public List<Vote> getAllWithRestaurantByUserId(int userId) {
        return this.voteRepository.getAllWithRestaurantByUserId(userId);
    }

    @Override
    public List<Vote> getAllWithUserByRestaurantId(int restaurantId) {
        return this.voteRepository.getAllWithUserByRestaurantId(restaurantId);
    }

    @Override
    public Vote getByUserIdAndDate(LocalDate localDate, int userId) {
        return this.voteRepository.getByUserIdAndDate(localDate, userId);
    }


    @Override
    public Vote getWithRestaurantByUserIdAndDate(LocalDate date, int userId) {
        return this.voteRepository.getWithRestaurantByUserIdAndDate(date, userId);
    }

    @Override
    public List<Vote> getWithUserByRestaurantIdAndDate(LocalDate date, int restaurantId) {
        return this.voteRepository.getWithUserByRestaurantIdByDate(date, restaurantId);
    }

    @Override
    public int getVotesCount(LocalDate date, int restaurantId) {
        return this.voteRepository.getVotesCount(date, restaurantId);
    }
}
