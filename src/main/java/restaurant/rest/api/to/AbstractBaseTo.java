package restaurant.rest.api.to;

import restaurant.rest.api.model.AbstractBaseEntity;

public abstract class AbstractBaseTo {
    protected abstract AbstractBaseEntity toEntity();
    protected abstract AbstractBaseTo toDto(AbstractBaseEntity entity);

}
