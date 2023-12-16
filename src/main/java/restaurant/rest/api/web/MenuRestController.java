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
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.service.MenuService;
import restaurant.rest.api.to.MenuTo;


import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {

    @Autowired
    private MenuService service;

    static final String REST_ADMIN_URL = "/rest/admin/restaurants/{restaurantId}/menus", REST_URL = "/rest/restaurant/{restaurantId}/menus";

    private static final Logger log = LoggerFactory.getLogger(MenuRestController.class);


    @GetMapping(REST_ADMIN_URL)
    public List<Menu> getAll(@PathVariable int restaurantId){
        log.info("getAll");
        return service.getAll(restaurantId);
    }

    @GetMapping(REST_ADMIN_URL + "/{id}")
    public Menu get(@PathVariable int restaurantId, @PathVariable int id){
        log.info("getAll {}", id);
        return service.get(id, restaurantId);
    }

    @DeleteMapping(REST_ADMIN_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id){
        service.delete(id, restaurantId);
    }

    @GetMapping(REST_ADMIN_URL + "/by-date")
    public Menu getMenuByDate(@PathVariable int restaurantId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        log.info("getByDate {}", date);
        return service.getMenuByDate(date, restaurantId);
    }

    @PostMapping(value = REST_ADMIN_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@PathVariable int restaurantId, @RequestBody MenuTo menuTo) {
        Menu created = this.service.create(menuTo.toEntity(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = REST_ADMIN_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @RequestBody MenuTo menuTo, @PathVariable int id) {
        log.info("update {} with id={}", menuTo, id);
        Menu menu = menuTo.toEntity();
        assureIdConsistent(menu, id);
        this.service.update(menu, restaurantId);
    }


    @GetMapping(REST_URL)
    public Menu getActualMenu(@PathVariable int restaurantId){
        log.info("getActualMenu {}", restaurantId);
        return service.getActualMenu(restaurantId);
    }



}
