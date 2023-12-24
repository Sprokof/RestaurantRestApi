package restaurant.rest.api.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class RestaurantTo extends AbstractBaseTo {
    private String name;
    private String description;
    private int countVotes;
    private List<MenuTo> menuTos;

    @Override
    public Restaurant toEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(this.getId());
        restaurant.setDescription(this.description);
        restaurant.setName(this.name);
        restaurant.setMenus(convertToEntity());
        return restaurant;
    }

    private List<Menu> convertToEntity() {
        return this.menuTos.stream()
                .map(MenuTo::toEntity)
                .collect(Collectors.toList());
    }






}
