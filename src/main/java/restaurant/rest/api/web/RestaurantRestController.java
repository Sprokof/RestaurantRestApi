package restaurant.rest.api.web;

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
public class RestaurantRestController {

    static final String REST_URL = "/rest/restaurants", REST_ADMIN_URL = "/rest/admin/restaurants";

    private static final Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    @Autowired
    private RestaurantService service;


    @GetMapping(REST_URL)
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestaurantUtil.toDtos(this.service.getAll(), this.service);
    }

    @GetMapping(REST_URL + "/by-name")
    public List<RestaurantTo> getAllByName(@RequestParam String name) {
        log.info("getAll with name={}", name);
        return RestaurantUtil.toDtos(this.service.getAllByName(name), service);
    }

    @GetMapping(REST_URL + "/{id}/with-menu")
    public RestaurantTo getWithMenu(@PathVariable int id) {
        log.info("get {}", id);
        return RestaurantUtil.toDto(service.getWithMenu(id), service.getVotesCount(id));
    }

    @GetMapping(REST_URL + "/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("get {}", id);
        return RestaurantUtil.toDto(service.get(id), service.getVotesCount(id));
    }

    @GetMapping(REST_URL + "/{id}/with-menu/by-date")
    public List<RestaurantTo> getAllWithMenuByDate(@PathVariable int id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll {}", id);
        return RestaurantUtil.toDtos(service.getAllWithMenuByDate(date), service);
    }

    @GetMapping(REST_URL)
    public List<RestaurantTo> getTop(@RequestParam int top) {
        log.info("getTop");
        List<Restaurant> restaurants = this.service.getActualTop(top);
        return RestaurantUtil.toDtos(restaurants, service);
    }

    @GetMapping(REST_ADMIN_URL + "/by-date")
    public List<RestaurantTo> getTopByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam int top) {
        List<Restaurant> restaurants = this.service.getTopByDate(date, top);
        return RestaurantUtil.toDtos(restaurants, service);
    }

    @PostMapping(value = REST_ADMIN_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody RestaurantTo restaurantTo) {
        Restaurant created = this.service.create(restaurantTo.toEntity());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(REST_ADMIN_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        this.service.delete(id);
    }

    @PutMapping(value = REST_ADMIN_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update {} with id={}", restaurantTo, id);
        Restaurant restaurant = restaurantTo.toEntity();
        assureIdConsistent(restaurant, id);
        this.service.update(restaurant);
    }



}
