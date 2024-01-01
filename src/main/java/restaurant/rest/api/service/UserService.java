package restaurant.rest.api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import restaurant.rest.api.AuthorizedUser;
import restaurant.rest.api.model.User;
import restaurant.rest.api.repository.UserRepository;

import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.*;
import static restaurant.rest.api.util.ValidationUtil.checkNotFoundWithId;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

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

    @Override
    public AuthorizedUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.getByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("user with username " + username + " not found");
        }
        return new AuthorizedUser(user);
    }

    public User getByUsername(String username){
        return this.repository.getByUsername(username);
    }

    public User getWithVotes(int id){
        return checkNotFoundWithId(this.repository.getWithVotes(id), id);
    }
    public User getWithLastVote(int id){
        return checkNotFoundWithId(this.repository.getWithLastVote(id), id);

    }
}
