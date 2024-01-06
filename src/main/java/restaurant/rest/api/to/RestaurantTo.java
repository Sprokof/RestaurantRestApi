package restaurant.rest.api.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.Vote;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Restaurant's entity")
public class RestaurantTo extends AbstractBaseTo {
    private String name;
    private String description;
    private int countVotes;
    private List<MenuTo> menuTos;
    private Set<VoteTo> voteTos;

    @Override
    public Restaurant toEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(this.getId());
        restaurant.setDescription(this.description);
        restaurant.setName(this.name);
        restaurant.setMenus(convertMenuToEntity());
        restaurant.setVotes(convertVoteToEntity());
        return restaurant;
    }

    private List<Menu> convertMenuToEntity() {
            return this.menuTos.stream()
                    .map(MenuTo::toEntity)
                    .collect(Collectors.toList());
    }
    private Set<Vote> convertVoteToEntity() {
        return this.voteTos.stream()
                .map(VoteTo::toEntity)
                .collect(Collectors.toSet());
    }


    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", countVotes=" + countVotes +
                ", menuTos=" + menuTos +
                ", voteTos=" + voteTos +
                '}';
    }
}
