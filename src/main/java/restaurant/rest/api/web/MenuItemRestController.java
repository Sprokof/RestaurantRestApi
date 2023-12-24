package restaurant.rest.api.web;

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

import java.net.URI;
import java.util.Set;

import static restaurant.rest.api.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = MenuItemRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemRestController {
    static final String REST_URL = "/rest/admin/menu{menuId}/menu-items";

    private static final Logger log = LoggerFactory.getLogger(MenuRestController.class);


    @Autowired
    private MenuItemService service;

    @GetMapping("/{id}")
    public MenuItem get(@PathVariable int menuId, @PathVariable int id){
        log.info("get {}", id);
        return this.service.get(id, menuId);
    }

    @GetMapping()
    public Set<MenuItem> getAll(@PathVariable int menuId){
        log.info("getAll");
        return this.service.getAll(menuId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int menuId, @PathVariable int id){
        log.info("delete {}", id);
        this.service.delete(id, menuId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@PathVariable int menuId, @RequestBody MenuItemTo menuItemTo) {
        MenuItem created = this.service.create(menuItemTo.toEntity(), menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int menuId, @RequestBody MenuItemTo menuItemTo, @PathVariable int id) {
        log.info("update {} with id={}", menuItemTo, id);
        MenuItem menuItem = menuItemTo.toEntity();
        assureIdConsistent(menuItem, id);
        this.service.update(menuItem, menuId);
    }

}
