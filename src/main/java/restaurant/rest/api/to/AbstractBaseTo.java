package restaurant.rest.api.to;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import restaurant.rest.api.model.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class AbstractBaseTo {

    private Integer id;
    protected abstract AbstractBaseEntity toEntity();
    protected abstract AbstractBaseTo toDto(AbstractBaseEntity entity);

    public static AbstractBaseTo asDto(AbstractBaseEntity entity){
        AbstractBaseTo baseTo = new UserTo();
        if(entity instanceof Restaurant) {
            baseTo = new RestaurantTo();
        }

        if(entity instanceof Menu) {
            baseTo = new MenuItemTo();
        }

        if(entity instanceof Vote){
            baseTo = new VoteTo();
        }

        if(entity instanceof MenuItem){
            baseTo = new MenuItemTo();
        }
        return baseTo.toDto(entity);
    }
    public int id(){
        Assert.notNull(id, "id must not null");
        return id;
    }
}
