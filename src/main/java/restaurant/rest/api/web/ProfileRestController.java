package restaurant.rest.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
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
@Tag(name="Профиль", description="Управление профилем пользователя")
@SecurityRequirement(name = "basicAuth")
public class ProfileRestController {
    static final String REST_URL = "/rest/profile";

    private static final Logger log = LoggerFactory.getLogger(ProfileRestController.class);


    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(
            summary = "Получение пользователя",
            description = "Позволяет получить пользователя"
    )
    public User get() {
        log.info("get {}", authUserId());
        return userService.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Уадение пользователя",
            description = "Позволяет удалить пользователя"
    )
    public void delete() {
        log.info("delete {}", authUserId());
        userService.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Обновление пользователя",
            description = "Позволяет обновить пользователя"
    )
    public void update(@RequestBody @NotNull @Parameter(description = "сущность пользователя") UserTo userTo) {
        User user = userTo.toEntity();
        log.info("update {} with id={}", user, authUserId());
        assureIdConsistent(user, authUserId());
        userService.update(user);
    }


    @GetMapping("/with-votes")
    @Operation(
            summary = "Получение пользователя вместе с его голосами",
            description = "Позволяет получить пользователя вместе с его голосами"
    )
    public UserTo getWithVotes(){
        log.info("getWithVotes");
        return UserUtil.toDtoWithVotes(userService.getWithVotes(authUserId()));
    }

    @GetMapping("/with-last-vote")
    @Operation(
            summary = "Получение пользователя вместе с его последним голосом",
            description = "Позволяет получить пользователя вместе с его последним голосом"
    )
    public UserTo getWithLastVote() {
        log.info("getWithLastVote");
        return UserUtil.toDtoWithVotes(userService.getWithLastVote(authUserId()));
    }


}
