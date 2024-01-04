package restaurant.rest.api.to;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import restaurant.rest.api.model.*;

import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@Setter
public abstract class AbstractBaseTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    protected abstract AbstractBaseEntity toEntity();

    public int id(){
        Assert.notNull(id, "id must not null");
        return id;
    }
}
