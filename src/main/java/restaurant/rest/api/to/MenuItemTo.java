package restaurant.rest.api.to;

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
    public AbstractBaseEntity toEntity() {
        MenuItem item = new MenuItem();
        item.setId(this.getId());
        item.setDish(this.dish);
        item.setPrice(this.price);
        return item;
    }

    @Override
    public AbstractBaseTo toDto(AbstractBaseEntity entity) {
        MenuItem item = (MenuItem) entity;
        this.setId(item.getId());
        this.setDish(item.getDish());
        this.setPrice(item.getPrice());
        return this;
    }
}
