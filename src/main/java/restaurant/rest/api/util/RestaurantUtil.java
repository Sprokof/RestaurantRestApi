package restaurant.rest.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.to.MenuTo;
import restaurant.rest.api.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static RestaurantTo toDto(Restaurant restaurant, int votesCount) {
        RestaurantTo to = new RestaurantTo();
        to.setId(restaurant.id());
        to.setName(restaurant.getName());
        to.setDescription(restaurant.getDescription());
        to.setCountVotes(votesCount);
        to.setMenuTos(convertMenu(restaurant.getMenus()));
        return to;
    }

    public static List<RestaurantTo> toDtos(List<Restaurant> restaurants, RestaurantService service){
        return restaurants.stream()
                .map(r -> RestaurantUtil.toDto(r, service.getVotesCount(r.id())))
                .collect(Collectors.toList());
    }

    private static List<MenuTo> convertMenu(List<Menu> menus){
        return menus.stream()
                .map(MenuUtil::toDto)
                .collect(Collectors.toList());
    }
}
