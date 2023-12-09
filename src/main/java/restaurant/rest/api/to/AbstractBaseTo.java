package restaurant.rest.api.to;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import restaurant.rest.api.model.AbstractBaseEntity;

@Getter
@Setter
public abstract class AbstractBaseTo {
    private Integer id;
    protected abstract AbstractBaseEntity toEntity();
    protected abstract AbstractBaseTo toDto(AbstractBaseEntity entity);

    public int id(){
        Assert.notNull(id, "id must not null");
        return id;
    }
}
