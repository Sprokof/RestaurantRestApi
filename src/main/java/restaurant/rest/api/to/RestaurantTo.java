package restaurant.rest.api.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Restaurant;

@NoArgsConstructor
@Getter
@Setter
public class RestaurantTo extends AbstractBaseTo {
    private String name;
    private String description;
    private int countVotes;
    private MenuTo menuTo;

    @Override
    public Restaurant toEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(this.getId());
        restaurant.setDescription(this.description);
        restaurant.setName(this.name);
        restaurant.addMenu(this.menuTo.toEntity());
        return restaurant;
    }





}
