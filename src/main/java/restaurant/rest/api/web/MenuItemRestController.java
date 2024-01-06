package restaurant.rest.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.service.MenuItemService;
import restaurant.rest.api.to.MenuItemTo;
import restaurant.rest.api.util.MenuItemUtil;
import restaurant.rest.api.util.MenuUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.net.URI;
import java.util.Set;

import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = MenuItemRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "basicAuth")
@Tag(name="Menu's items", description="Manage menu's items")
public class MenuItemRestController {
    static final String REST_URL = "/rest/admin/menu{menuId}/menu-items";

    private static final Logger log = LoggerFactory.getLogger(MenuItemRestController.class);


    @Autowired
    private MenuItemService service;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get menu's item given menu by id",
            description = "let to get menu's item given menu by id"
    )
    public MenuItemTo get(@PathVariable @Min(1) @Parameter(description = "menu's ID") int menuId,
                          @PathVariable @Min(1) @Parameter(description = "menu item ID") int id){
        log.info("get {}", id);
        return MenuItemUtil.toDto(this.service.get(id, menuId));
    }

    @GetMapping()
    @Operation(
            summary = "Get all menu's item given menu",
            description = "Let to get all menu's item given menu"
    )
    public Set<MenuItemTo> getAll(@PathVariable @Min(1) @Parameter(description = "menu's ID") int menuId){
        log.info("getAll");
        return MenuItemUtil.toDtos(this.service.getAll(menuId));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete menu's item given menu",
            description = "let to get menu's item given menu"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Parameter(description = "menu's ID") int menuId,
                       @PathVariable @Min(1) @Parameter(description = "menu item ID") int id){
        log.info("delete {}", id);
        this.service.delete(id, menuId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create new menu's item for given menu",
            description = "Let to create new menu's item for given menu"
    )
    public ResponseEntity<MenuItemTo> createWithLocation(@PathVariable @Min(1) @Parameter(description = "menu's ID") int menuId,
                                                         @RequestBody @NotNull @Parameter(description = "menu's item entity") MenuItemTo menuItemTo) {
        MenuItem created = this.service.create(menuItemTo.toEntity(), menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MenuUtil.replacePathVariable(REST_URL, menuId) + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(MenuItemUtil.toDto(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update new menu's item for given menu",
            description = "Let to update new menu's item for given menu"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable @Min(1) @Parameter(description = "menu's ID") int menuId,
                       @RequestBody @NotNull @Parameter(description = "menu's item entity") MenuItemTo menuItemTo, @PathVariable int id) {
        log.info("update {} with id={}", menuItemTo, id);
        MenuItem menuItem = menuItemTo.toEntity();
        assureIdConsistent(menuItem, id);
        this.service.update(menuItem, menuId);
    }

}
