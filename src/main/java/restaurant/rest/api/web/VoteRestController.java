package restaurant.rest.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.service.VoteService;
import restaurant.rest.api.to.VoteTo;
import restaurant.rest.api.util.VoteUtil;
import restaurant.rest.api.util.exception.ResourceNotUpdatedException;

import java.net.URI;
import java.util.List;

import static restaurant.rest.api.util.SecurityUtil.authUserId;
import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    private static final Logger log = LoggerFactory.getLogger(VoteRestController.class);

    static final String PROFILE_REST_URL = "/rest/profile/votes", RESTAURANT_REST_URL = "/rest/restaurant/{restaurantId}/votes";

    @Autowired
    private VoteService voteService;

    @GetMapping(PROFILE_REST_URL + "/with-restaurant")
    public List<Vote> getAllWithRestaurant() {
        log.info("getAll");
        return voteService.getAllWithRestaurantByUserId(authUserId());
    }

    @GetMapping(PROFILE_REST_URL)
    public List<Vote> getAll(){
        log.info("getAll");
        return this.voteService.getAll(authUserId());
    }

    @GetMapping(PROFILE_REST_URL + "/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get {}", id);
        return voteService.get(id, authUserId());
    }


    @GetMapping(PROFILE_REST_URL + "/actual")
    public Vote getActual() {
        log.info("getActual");
        return voteService.getActual(authUserId());
    }

    @GetMapping(PROFILE_REST_URL + "/actual/with-restaurant")
    public Vote getActualWithRestaurant() {
        log.info("getActualWithRestaurant");
        return voteService.getActualWithRestaurantByUserId(authUserId());
    }


    @GetMapping(PROFILE_REST_URL + "/{id}")
    public Vote getWithRestaurant(@PathVariable int id) {
        log.info("get {}", id);
        return voteService.getWithRestaurant(id, authUserId());
    }

    @DeleteMapping(PROFILE_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote {} for user {}", id, authUserId());
        this.voteService.delete(id, authUserId());
    }


    @GetMapping(RESTAURANT_REST_URL)
    public List<Vote> getAllActualWithUser(@PathVariable int restaurantId) {
        log.info("getAll");
        return voteService.getAllActualWithUserByRestaurantId(restaurantId);
    }

    @PostMapping(value = RESTAURANT_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@PathVariable int restaurantId, @RequestBody VoteTo voteTo) {
        Vote created = this.voteService.create(voteTo.toEntity(), authUserId(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_REST_URL + "/" + restaurantId + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = RESTAURANT_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @RequestBody VoteTo voteTo, @PathVariable int id) {
        if(VoteUtil.accept(voteTo.getVoteTime())){
            throw new ResourceNotUpdatedException("Resource can not be updated");
        }
        log.info("update {} with id={}", voteTo, id);
        Vote vote = voteTo.toEntity();
        assureIdConsistent(vote, id);
        voteService.update(vote, authUserId(), restaurantId);
    }


}
