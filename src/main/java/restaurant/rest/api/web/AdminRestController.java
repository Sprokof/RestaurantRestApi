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
@Tag(name="Администратор", description="Управление пользователями")
@SecurityRequirement(name = "basicAuth")
public class AdminRestController {
    static final String REST_URL = "/rest/admin/users";
    private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(
            summary = "Получение пользователей",
            description = "Позволяет получить всех пользователей"
    )
    public List<UserTo> getAll() {
        log.info("getAll");
        return UserUtil.toDtos(userService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение пользователя по id",
            description = "Позволяет получить пользователя по id"
    )
    public UserTo get(@PathVariable int id) {
        log.info("get {}", id);
        return UserUtil.toDtoWithVotes(userService.get(id));
    }

    @GetMapping("/{id}/with-votes")
    @Operation(
            summary = "Получение пользователя по id вместе с его голосами",
            description = "Позволяет получить пользователя по id вместе с его голосами"
    )
    public UserTo getWithVotes(@PathVariable @Min(1) @Parameter(description = "Идентификатор пользователя") int id){
        log.info("get {}", id);
        return UserUtil.toDtoWithVotes(userService.getWithVotes(id));
    }
    @GetMapping("/{id}/with-last-vote")
    @Operation(
            summary = "Получение пользователя по id вместе с его последним голосом",
            description = "Позволяет получить пользователя по id вместе с его последним голосом"
    )
    public UserTo getWithLastVote(@PathVariable @Min(1) @Parameter(description = "Идентификатор пользователя") int id) {
        log.info("get {}", id);
        return UserUtil.toDtoWithVotes(userService.getWithLastVote(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Добавление нового пользователя",
            description = "Позволяет добавить нового пользователя"
    )
    public ResponseEntity<UserTo> createWithLocation(@RequestBody @NotNull @Parameter(description = "сущность пользователя") UserTo userTo) {
        User created = this.userService.create(userTo.toEntity() );
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(UserUtil.toDto(created));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление пользователя по id",
            description = "Позволяет удалить пользователя по id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Parameter(description = "индентификатор пользователя") int id) {
        this.userService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Обновление пользователя по id",
            description = "Позволяет обновить пользователя по id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @NotNull @Parameter(description = "сущность пользователя") UserTo userTo,
                       @PathVariable @Min(1) @Parameter(description = "индентификатор пользователя") int id) {
        log.info("update {} with id={}", userTo, id);
        User user = userTo.toEntity();
        assureIdConsistent(user, id);
        userService.update(user);
    }

    @GetMapping(value = "/by-email")
    @Operation(
            summary = "Получение пользователя по email",
            description = "Позволяет получить пользователя по email"
    )
    public UserTo getByMail(@RequestParam @Email @Parameter(description = "email пользователя") String email) {
        log.info("get {}", email);
        return UserUtil.toDto(userService.getByEmail(email));
    }

    @GetMapping(value = "/by-username")
    @Operation(
            summary = "Получение пользователя по username",
            description = "Позволяет получить пользователя по username"
    )
    public UserTo getByUsername(@RequestParam @NotBlank @Parameter(description = "username пользователя")String username) {
        log.info("get {}", username);
        return UserUtil.toDto(userService.getByUsername(username));
    }
}
