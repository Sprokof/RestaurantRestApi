package restaurant.rest.api.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.MenuItem;

@Getter
@Setter
@NoArgsConstructor
public class MenuItemTo extends AbstractBaseTo {
    private String dish;
    private double price;

    @Override
    public MenuItem toEntity() {
        MenuItem item = new MenuItem();
        item.setId(this.getId());
        item.setDish(this.dish);
        item.setPrice(this.price);
        return item;
    }

}
