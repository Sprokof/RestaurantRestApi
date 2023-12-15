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
    private LocalDate date;
    private LocalTime time;
    private List<MenuItemTo> items;

    @Override
    public Menu toEntity() {
        Menu menu = new Menu();
        menu.setId(this.getId());
        menu.setDate(this.date);
        menu.setMenuItems(convertToEntity());
        return null;
    }

    private List<MenuItem> convertToEntity(){
        return this.items.stream()
                .map(i -> (MenuItem) i.toEntity())
                .collect(Collectors.toList());
    }



}
