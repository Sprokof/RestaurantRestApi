package restaurant.rest.api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import restaurant.rest.api.model.User;
import restaurant.rest.api.repository.UserRepository;

import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.*;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User create(User user){
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user){
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    public User get(int id){
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void delete(int id){
        checkNotFoundWithId(repository.delete(id), id);
    }


    @Cacheable(value = "users")
    public List<User> getAll(){
        return this.repository.getAll();
    }

    public User getByEmail(String email){
        return this.repository.getByEmail(email);
    }
}