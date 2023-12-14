package restaurant.rest.api.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Restaurant;

@NoArgsConstructor
@Getter
@Setter
public class RestaurantTo extends AbstractBaseTo {
    private String name;
    private String description;
    private int countVotes;

    @Override
    public AbstractBaseEntity toEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(this.getId());
        restaurant.setDescription(this.description);
        restaurant.setName(this.name);
        restaurant.setCountVotes(this.countVotes);
        return restaurant;
    }

    public AbstractBaseTo toDto(AbstractBaseEntity entity) {
        Restaurant restaurant = (Restaurant) entity;
        this.setId(restaurant.getId());
        this.setName(restaurant.getName());
        this.setDescription(restaurant.getDescription());
        this.setCountVotes(restaurant.getCountVotes());
        return this;
    }

}
