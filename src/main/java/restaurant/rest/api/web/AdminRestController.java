package restaurant.rest.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
import restaurant.rest.api.util.UserUtil;


import java.net.URI;
import java.util.List;
import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Administrator", description="Manage users")
@SecurityRequirement(name = "basicAuth")
public class AdminRestController {
    static final String REST_URL = "/rest/admin/users";
    private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Let to get all users"
    )
    public List<UserTo> getAll() {
        log.info("getAll");
        return UserUtil.toDtos(userService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by id",
            description = "Let to get user by id"
    )
    public UserTo get(@PathVariable int id) {
        log.info("get {}", id);
        return UserUtil.toDtoWithVotes(userService.get(id));
    }

    @GetMapping("/{id}/with-votes")
    @Operation(
            summary = "Get users by id with votes",
            description = "Let to get user by id with votes"
    )
    public UserTo getWithVotes(@PathVariable @Min(1) @Parameter(description = "user's ID") int id){
        log.info("get {}", id);
        return UserUtil.toDtoWithVotes(userService.getWithVotes(id));
    }
    @GetMapping("/{id}/with-last-vote")
    @Operation(
            summary = "Get user by id with last votes",
            description = "Let get user by id with last votes"
    )
    public UserTo getWithLastVote(@PathVariable @Min(1) @Parameter(description = "user's ID") int id) {
        log.info("get {}", id);
        return UserUtil.toDtoWithVotes(userService.getWithLastVote(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create new user",
            description = "Let to create new user"
    )
    public ResponseEntity<UserTo> createWithLocation(@RequestBody @NotNull @Parameter(description = "user's entity") UserTo userTo) {
        User created = this.userService.create(userTo.toEntity() );
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(UserUtil.toDto(created));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user by id",
            description = "Let delete user by id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Parameter(description = "user's ID") int id) {
        this.userService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update user by id",
            description = "Let to update user by id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @NotNull @Parameter(description = "user's entity") UserTo userTo,
                       @PathVariable @Min(1) @Parameter(description = "user's ID") int id) {
        log.info("update {} with id={}", userTo, id);
        User user = userTo.toEntity();
        assureIdConsistent(user, id);
        userService.update(user);
    }

    @GetMapping(value = "/by-email")
    @Operation(
            summary = "Get user by email",
            description = "Let to get user by email"
    )
    public UserTo getByMail(@RequestParam @Email @Parameter(description = "user's email") String email) {
        log.info("get {}", email);
        return UserUtil.toDto(userService.getByEmail(email));
    }

    @GetMapping(value = "/by-username")
    @Operation(
            summary = "Get user by username",
            description = "Let to get user by username"
    )
    public UserTo getByUsername(@RequestParam @NotBlank @Parameter(description = "user's username")String username) {
        log.info("get {}", username);
        return UserUtil.toDto(userService.getByUsername(username));
    }
}
