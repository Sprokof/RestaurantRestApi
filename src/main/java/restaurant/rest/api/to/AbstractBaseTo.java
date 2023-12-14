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

    public int id(){
        Assert.notNull(id, "id must not null");
        return id;
    }
}
