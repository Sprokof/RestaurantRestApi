package restaurant.rest.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.to.RestaurantTo;
import restaurant.rest.api.util.RestaurantUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@Tag(name="Ресторан", description="Управление рестораном")
@SecurityRequirement(name = "basicAuth")
public class RestaurantRestController {

    static final String REST_URL = "/rest/restaurants", REST_ADMIN_URL = "/rest/admin/restaurants";

    private static final Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    @Autowired
    private RestaurantService service;


    @GetMapping(REST_URL)
    @Operation(
            summary = "Получение всех ресторанов",
            description = "Позволяет получить все рестораны"
    )
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestaurantUtil.toDtos(this.service.getAll(), this.service);
    }

    @GetMapping(REST_URL + "/by-name")
    @Operation(
            summary = "Получение всех ресторанов по имени",
            description = "Позволяет получить все рестораны по имени"
    )
    public List<RestaurantTo> getAllByName(@RequestParam @NotBlank @Parameter(description = "имя ресторана") String name) {
        log.info("getAll with name={}", name);
        return RestaurantUtil.toDtos(this.service.getAllByName(name), service);
    }

    @GetMapping(REST_ADMIN_URL + "/{id}/with-votes")
    @Operation(
            summary = "Получение ресторана по id вместе со всеми голосами",
            description = "Позволяет получить ресторан по id вместе со всеми голосами"
    )
    public RestaurantTo getWithVotes(@PathVariable @Min(1) @Parameter(description = "идентификатор ресторана") int id){
        return RestaurantUtil.toDtoWithVotes(this.service.getWithVotes(id), service.getVotesCount(id));
    }

    @GetMapping(REST_URL + "/{id}/with-last-votes")
    @Operation(
            summary = "Получение ресторана по id вместе с последними голосами",
            description = "Позволяет получить ресторан по id вместе с последними голосами"
    )
    public RestaurantTo getWithLastVotes(@PathVariable @Min(1) @Parameter(description = "идентификатор ресторана") int id){
        return RestaurantUtil.toDtoWithVotes(this.service.getWithVotes(id), service.getVotesCount(id));
    }


    @GetMapping(REST_URL + "/{id}/with-menu")
    @Operation(
            summary = "Получение ресторана по id вместе с последним меню",
            description = "Позволяет получить ресторан по id вместе с последним меню"
    )
    public RestaurantTo getWithMenu(@PathVariable @Min(1) @Parameter(description = "идентификатор ресторана") int id) {
        log.info("get {}", id);
        return RestaurantUtil.toDtoWithMenus(service.getWithMenu(id), service.getVotesCount(id));
    }

    @GetMapping(REST_URL + "/{id}")
    @Operation(
            summary = "Получение ресторана по id",
            description = "Позволяет получить ресторан по id"
    )
    public RestaurantTo get(@PathVariable @Min(1) @Parameter(description = "идентификатор ресторана")  int id) {
        log.info("get {}", id);
        return RestaurantUtil.toDto(service.get(id), service.getVotesCount(id));
    }

    @GetMapping(REST_URL + "/with-menu/by-date")
    @Operation(
            summary = "Получение всех ресторанов вместе с меню дате",
            description = "Позволяет получить все рестораны вместе с меню по дате обновления или создания меню"
    )
    public List<RestaurantTo> getAllWithMenuByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "дата создания меню", example = "yyyy-MM-dd") LocalDate date) {
        log.info("getAllWithMenuDyDate {}", date);
        return RestaurantUtil.toDtosWithMenus(service.getAllWithMenuByDate(date), service);
    }

    @GetMapping(REST_URL + "/top")
    @Operation(
            summary = "Получение последнего топа ресторанов",
            description = "Позволяет получить последний топ ресторанов"
    )
    public List<RestaurantTo> getTop(@RequestParam @Min(1) @Parameter(description = "количество в топе") int top) {
        log.info("getTop");
        List<Restaurant> restaurants = this.service.getActualTop(top);
        return RestaurantUtil.toDtos(restaurants, service);
    }

    @GetMapping(REST_ADMIN_URL + "/top")
    @Operation(
            summary = "Получение топа ресторанов по дате",
            description = "Позволяет получить топ ресторанов на заданную дату"
    )
    public List<RestaurantTo> getTopByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "дата создания голоса", example = "yyyy-MM-dd") LocalDate date, @RequestParam @Min(1) @Parameter(description = "количество в топе") int top) {
        List<Restaurant> restaurants = this.service.getTopByDate(date, top);
        return RestaurantUtil.toDtos(restaurants, service);
    }

    @PostMapping(value = REST_ADMIN_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Создание нового ресторана",
            description = "Позволяет создать новый ресторан"
    )
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody @NotNull @Parameter(description = "сущность ресторана") RestaurantTo restaurantTo) {
        Restaurant created = this.service.create(restaurantTo.toEntity());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(REST_ADMIN_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Удаление ресторана по id",
            description = "Позволяет создать новый ресторан"
    )
    public void delete(@PathVariable @Min(1) @Parameter(description = "индентификатор ресторана") int id) {
        log.info("delete {}", id);
        this.service.delete(id);
    }

    @PutMapping(value = REST_ADMIN_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Обновление ресторана по id",
            description = "Позволяет обновить ресторан по id"
    )
    public void update(@RequestBody @NotNull @Parameter(description = "сущность ресторана") RestaurantTo restaurantTo, @PathVariable @Min(1) @Parameter(description = "индентификатор ресторана") int id) {
        Restaurant restaurant = restaurantTo.toEntity();
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        this.service.update(restaurant);
    }



}
