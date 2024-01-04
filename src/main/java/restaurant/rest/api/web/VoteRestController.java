package restaurant.rest.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
import restaurant.rest.api.util.RestaurantUtil;
import restaurant.rest.api.util.VoteUtil;
import restaurant.rest.api.util.exception.ResourceNotUpdatedException;

import java.net.URI;
import java.util.Set;

import static restaurant.rest.api.util.SecurityUtil.authUserId;
import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Голоса", description="Управление голосами")
@SecurityRequirement(name = "basicAuth")
public class VoteRestController {
    private static final Logger log = LoggerFactory.getLogger(VoteRestController.class);

    static final String PROFILE_REST_URL = "/rest/profile/votes",
                        RESTAURANT_REST_URL = "/rest/restaurant/{restaurantId}/votes",
                        ADMIN_RESTAURANT_REST_URL = "/rest/admin/restaurant/{restaurantId}/votes",
                        ADMIN_REST_URL = "/rest/admin/users/{userId}/votes";

    @Autowired
    private VoteService voteService;

    @GetMapping(RESTAURANT_REST_URL + "/{id}")
    @Operation(
            summary = "Получение голоса по id",
            description = "Позволяет получить голос по id и restaurantId вместе с пользователем"
    )
    public VoteTo getByRestaurantId(@PathVariable @Min(1) @Parameter(description = "индентификатор голоса") int id,
                                    @PathVariable @Min(1) @Parameter(description = "индентификатор ресторана") int restaurantId){
        return VoteUtil.toDtoWithUser(this.voteService.getByRestaurantId(id, restaurantId));
    }

    @GetMapping(PROFILE_REST_URL + "/{id}")
    @Operation(
            summary = "Получение голоса по id",
            description = "Позволяет получить голос по id вместе с рестораном"
    )
    public VoteTo get(@PathVariable @Min(1) @Parameter(description = "индентификатор голоса") int id){
        return VoteUtil.toDtoWithRestaurant(this.voteService.getByUserId(id, authUserId()));
    }

    @GetMapping(PROFILE_REST_URL)
    @Operation(
            summary = "Получение всех голосов пользователя",
            description = "Позволяет получить все голоса вместе пользователя вместе с рестораном"
    )
    public Set<VoteTo> getAll(){
        return VoteUtil.toDtosWithRestaurant(this.voteService.getAllByUserId(authUserId()));
    }

    @GetMapping(ADMIN_REST_URL)
    @Operation(
            summary = "Получение всех голосов пользователя по userId",
            description = "Позволяет получить все голоса по userId пользователя вместе с рестораном"
    )
    public Set<VoteTo> getAllByUserId(@PathVariable @Min(1) @Parameter(description = "индентификатор пользователя") int userId){
        return VoteUtil.toDtosWithRestaurant(this.voteService.getAllByUserId(userId));
    }

    @GetMapping(ADMIN_RESTAURANT_REST_URL)
    @Operation(
            summary = "Получение всех голосов ресторана по restaurantId",
            description = "Позволяет получить все голоса ресторана по restaurantId ресторана вместе с пользователем"
    )
    public Set<VoteTo> getAllByRestaurantId(@PathVariable @Min(1) @Parameter(description = "индентификатор ресторана") int restaurantId){
        return VoteUtil.toDtosWithUser(this.voteService.getAllByRestaurantId(restaurantId));
    }

    @GetMapping(RESTAURANT_REST_URL)
    @Operation(
            summary = "Получение всех последних голосов ресторана по restaurantId",
            description = "Позволяет получить все последние ресторана по restaurantId ресторана вместе с пользователем"
    )
    public Set<VoteTo> getAllLastByRestaurantId(@PathVariable @Min(1) @Parameter(description = "индентификатор ресторана") int restaurantId){
        return VoteUtil.toDtosWithUser(this.voteService.getAllLastByRestaurantId(restaurantId));
    }

    @DeleteMapping(PROFILE_REST_URL + "/{id}")
    @Operation(
            summary = "Удаление голоса по id",
            description = "Позволяет удалить голос по id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Parameter(description = "индентификатор голоса") int id) {
        log.info("delete vote {} for user {}", id, authUserId());
        this.voteService.delete(id, authUserId());
    }


    @PostMapping(value = RESTAURANT_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@PathVariable @Min(1) @Parameter(description = "индентификатор ресторана") int restaurantId,
                                                   @RequestBody @NotNull @Parameter(description = "сущность голоса") VoteTo voteTo) {
        Vote created = this.voteService.create(voteTo.toEntity(), authUserId(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantUtil.replacePathVariable(RESTAURANT_REST_URL, restaurantId) + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = RESTAURANT_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable @Min(1) @Parameter(description = "индентификатор ресторана") int restaurantId,
                       @RequestBody @NotNull @Parameter(description = "сущность голоса") VoteTo voteTo, @PathVariable int id) {
        if(!VoteUtil.accept(voteTo.getVoteTime())){
            throw new ResourceNotUpdatedException("Resource can not be updated");
        }
        log.info("update {} with id={}", voteTo, id);
        Vote vote = voteTo.toEntity();
        assureIdConsistent(vote, id);
        voteService.update(vote, authUserId(), restaurantId);
    }



}
