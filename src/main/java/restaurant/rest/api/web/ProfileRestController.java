package restaurant.rest.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restaurant.rest.api.model.User;
import restaurant.rest.api.service.UserService;
import restaurant.rest.api.to.UserTo;
import restaurant.rest.api.util.UserUtil;

import static restaurant.rest.api.util.SecurityUtil.authUserId;
import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {
    static final String REST_URL = "/rest/profile";

    private static final Logger log = LoggerFactory.getLogger(ProfileRestController.class);


    @Autowired
    private UserService userService;

    @GetMapping
    public User get() {
        log.info("get {}", authUserId());
        return userService.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        log.info("delete {}", authUserId());
        userService.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        log.info("update {} with id={}", user, authUserId());
        assureIdConsistent(user, authUserId());
        userService.update(user);
    }


    @GetMapping("/with-votes")
    public UserTo getWithVotes(){
        log.info("getWithVotes");
        return UserUtil.toDtoWithVotes(userService.getWithVotes(authUserId()));
    }

    @GetMapping("/with-last-vote")
    public UserTo getWithLastVote() {
        log.info("getWithLastVote");
        return UserUtil.toDtoWithVotes(userService.getWithLastVote(authUserId()));
    }


}
