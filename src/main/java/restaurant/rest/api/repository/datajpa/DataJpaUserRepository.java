package restaurant.rest.api.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.rest.api.model.User;
import restaurant.rest.api.repository.UserRepository;

import java.util.List;

@Repository
@Transactional
public class DataJpaUserRepository implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository repository;

    @Autowired
    public DataJpaUserRepository(CrudUserRepository repository){
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        if(user.isNew()){
            return repository.save(user);
        }
        return get(user.id()) != null ? repository.save(user) : null;
    }

    @Override
    public User get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public List<User> getAll() {
        return this.repository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    @Override
    public User getWithVotes(int id) {
        return this.repository.getWithVotes(id);
    }

}
