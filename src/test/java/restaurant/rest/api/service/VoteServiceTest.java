package restaurant.rest.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.rest.api.config.ServiceTestConfig;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.util.exception.NotFoundException;

import java.util.List;

import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_ID;
import static restaurant.rest.api.data.UserTestData.USER_ID;
import static restaurant.rest.api.data.VoteTestData.*;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfig.class)
public class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void create(){
        Vote created = service.create(getNew(), USER_ID, RESTAURANT_ID);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, USER_ID), service.getWithRestaurant(newId, USER_ID));
    }

    @Test
    public void delete(){
        service.delete(CREATED_VOTE_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(CREATED_VOTE_ID, USER_ID));
    }

    @Test
    public void update(){
        Vote updated = getUpdated();
        service.update(updated, USER_ID + 1, RESTAURANT_ID + 1);
        VOTE_MATCHER.assertMatch(service.get(VOTE_ID + 1, USER_ID + 1), getUpdated());
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void get() {
        Vote vote = service.get(VOTE_ID, USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getAllByRestaurantId(){
        List<Vote> all = service.getAllWithUserByRestaurantId(RESTAURANT_ID);
        VOTE_MATCHER.assertMatch(all, List.of(VOTE_1));
    }

    @Test
    public void getAllByUserId(){
        List<Vote> all = service.getAllWithRestaurantByUserId(USER_ID + 1);
        VOTE_MATCHER.assertMatch(all, List.of(VOTE_2));
    }


}
