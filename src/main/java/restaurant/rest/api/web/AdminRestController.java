package restaurant.rest.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rest.api.model.User;
import restaurant.rest.api.service.UserService;
import restaurant.rest.api.to.UserTo;


import java.net.URI;
import java.util.List;
import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {
    static final String REST_URL = "/rest/admin/users";
    private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        log.info("getAll");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("get {}", id);
        return userService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody UserTo userTo) {
        User created = this.userService.create(userTo.toEntity());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        this.userService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @PathVariable int id) {
        log.info("update {} with id={}", userTo, id);
        User user = userTo.toEntity();
        assureIdConsistent(user, id);
        userService.update(user);
    }

    @GetMapping(value = "/by-email")
    public User getByMail(@RequestParam String email) {
        log.info("get {}", email);
        return userService.getByEmail(email);
    }

    @GetMapping(value = "/by-username")
    public User getByUsername(@RequestParam String username) {
        log.info("get {}", username);
        return userService.getByUsername(username);
    }
}
