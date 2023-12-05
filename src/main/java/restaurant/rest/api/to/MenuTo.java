package restaurant.rest.api.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.MenuItem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MenuTo extends AbstractBaseTo {
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private List<MenuItemTo> items;
    @Override
    protected AbstractBaseEntity toEntity() {
        Menu menu = new Menu();
        menu.setId(this.id);
        menu.setDate(this.date);
        menu.setMenuItems(convertToDto());
        return null;
    }

    @Override
    protected AbstractBaseTo toDto(AbstractBaseEntity entity) {
        Menu menu = (Menu) entity;
        this.setId(menu.getId());
        this.setDate(menu.getDate());
        this.setTime(menu.getTime());
        this.setItems(convertToEntity(menu.getMenuItems()));
        return this;
    }

    private List<MenuItem> convertToDto(){
        return this.items.stream()
                .map(i -> (MenuItem) i.toEntity())
                .collect(Collectors.toList());
    }

    private List<MenuItemTo> convertToEntity(List<MenuItem> items){
        return items.stream()
                .map(i -> (MenuItemTo) this.toDto(i))
                .collect(Collectors.toList());
    }


}
