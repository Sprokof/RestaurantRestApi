package restaurant.rest.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import restaurant.rest.api.config.ServiceTestConfig;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.util.exception.NotFoundException;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_MATCHER;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_ID;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_1;
import static restaurant.rest.api.data.UserTestData.USER_ID;
import static restaurant.rest.api.data.VoteTestData.*;

@SpringBootTest(classes = ServiceTestConfig.class)
public class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void create(){
        Vote created = service.create(getNew(), USER_ID, RESTAURANT_ID);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.getByUserId(newId, USER_ID), service.getByRestaurantId(newId, RESTAURANT_ID));
    }

    @Test
    void delete(){
        service.delete(CREATED_VOTE_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.getByUserId(CREATED_VOTE_ID, USER_ID));
    }

    @Test
    void update(){
        Vote updated = getUpdated();
        service.update(updated, USER_ID + 1, RESTAURANT_ID + 1);
        VOTE_MATCHER.assertMatch(service.getByUserId(VOTE_ID + 1, USER_ID + 1), getUpdated());
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    void getByUserId() {
        Vote vote = service.getByUserId(VOTE_ID, USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    void getByRestaurantId() {
        Vote vote = service.getByRestaurantId(VOTE_ID, RESTAURANT_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
        RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), RESTAURANT_1);
    }
    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByUserId(NOT_FOUND, USER_ID));
    }

    @Test
    void getAllByRestaurantId(){
        List<Vote> all = service.getAllByRestaurantId(RESTAURANT_ID);
        VOTE_MATCHER.assertMatch(all, List.of(VOTE_1));
    }

    @Test
    void getAllByUserId(){
        List<Vote> all = service.getAllByUserId(USER_ID + 1);
        VOTE_MATCHER.assertMatch(all, List.of(VOTE_2));
    }


}
