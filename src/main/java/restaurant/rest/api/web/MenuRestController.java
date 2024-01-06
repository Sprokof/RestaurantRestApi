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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.service.MenuService;
import restaurant.rest.api.to.MenuTo;
import restaurant.rest.api.util.MenuUtil;
import restaurant.rest.api.util.RestaurantUtil;
import restaurant.rest.api.util.exception.ResourceNotCreatedException;
import restaurant.rest.api.util.exception.ResourceNotUpdatedException;


import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Menu", description="Manage menu")
@SecurityRequirement(name = "basicAuth")
public class MenuRestController {

    @Autowired
    private MenuService service;

    static final String REST_ADMIN_URL = "/rest/admin/restaurants/{restaurantId}/menus", REST_URL = "/rest/restaurant/{restaurantId}/menus";

    private static final Logger log = LoggerFactory.getLogger(MenuRestController.class);


    @GetMapping(REST_ADMIN_URL)
    @Operation(
            summary = "Get all menus given restaurant",
            description = "Let to get all menus given restaurant"
    )
    public List<MenuTo> getAll(@PathVariable int restaurantId){
        log.info("getAll");
        return MenuUtil.toDtos(service.getAll(restaurantId));
    }

    @GetMapping(REST_ADMIN_URL + "/{id}")
    @Operation(
            summary = "Get menu given restaurant by id",
            description = "Let to get menu given restaurant by id"
    )
    public MenuTo get(@PathVariable @Min(1) @Parameter(description = "restaurant's ID") int restaurantId,
                      @PathVariable @Min(1) @Parameter(description = "menu's ID") int id){
        log.info("get {}", id);
        return MenuUtil.toDto(service.get(id, restaurantId));
    }

    @DeleteMapping(REST_ADMIN_URL + "/{id}")
    @Operation(
            summary = "Delete menu given restaurant by id",
            description = "Let to delete menu given restaurant by id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Parameter(description = "restaurant's ID") int restaurantId,
                       @PathVariable @Min(1) @Parameter(description = "menu's ID") int id){
        service.delete(id, restaurantId);
    }

    @GetMapping(REST_ADMIN_URL + "/by-date")
    @Operation(
            summary = "Get menu given restaurant by create/update date",
            description = "Let to get menu given restaurant by create/update date"
    )
    public MenuTo getByDate(@PathVariable @Min(1) @Parameter(description = "restaurant's ID") int restaurantId,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "menu create/update date", example = "yyyy-MM-dd") LocalDate date){
        log.info("getByDate {}", date);
        return MenuUtil.toDto(service.getByDate(date, restaurantId));
    }

    @PostMapping(value = REST_ADMIN_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create new menu for given restaurant",
            description = "Let to create new menu for given restaurant, if menu not created/updated"
    )
    public ResponseEntity<MenuTo> createWithLocation(@PathVariable @Min(1) @Parameter(description = "restaurant's ID") int restaurantId,
                                                     @RequestBody @NotNull @Parameter(description = "menu's entity") MenuTo menuTo) {
        Menu menu = menuTo.toEntity();
        if(service.exist(menu.getDate(), restaurantId)){
            throw new ResourceNotCreatedException("Resource can not be created");
        }
        Menu created = this.service.create(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantUtil.replacePathVariable(REST_ADMIN_URL, restaurantId) + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(MenuUtil.toDto(created));
    }

    @PutMapping(value = REST_ADMIN_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update menu given restaurant",
            description = "Let to update menu given restaurant, if menu not created/updated"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @RequestBody MenuTo menuTo, @PathVariable int id) {
        Menu menu = menuTo.toEntity();
        if(service.exist(menu.getDate(), restaurantId)){
            throw new ResourceNotUpdatedException("Resource can not be updated");
        }
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        this.service.update(menu, restaurantId);
    }


    @GetMapping(REST_URL)
    @Operation(
            summary = "Get last menu given restaurant",
            description = "Let to get last menu given restaurant"
    )
    public MenuTo getLast(@PathVariable int restaurantId){
        log.info("getLast {}", restaurantId);
        return MenuUtil.toDto(service.getLast(restaurantId));
    }



}
