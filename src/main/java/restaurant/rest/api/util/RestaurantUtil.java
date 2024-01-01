package restaurant.rest.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.to.MenuTo;
import restaurant.rest.api.to.RestaurantTo;
import restaurant.rest.api.to.VoteTo;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static RestaurantTo toDto(Restaurant restaurant, int votesCount) {
        if(restaurant == null) return null;
        RestaurantTo to = new RestaurantTo();
        to.setId(restaurant.getId());
        to.setName(restaurant.getName());
        to.setDescription(restaurant.getDescription());
        to.setCountVotes(votesCount);
        to.setMenuTos(new ArrayList<>());
        to.setVoteTos(new HashSet<>());
        return to;
    }

    public static RestaurantTo toDtoWithMenus(Restaurant restaurant, int votesCount) {
        RestaurantTo to = toDto(restaurant, votesCount);
        to.setMenuTos(convertMenus(restaurant.getMenus()));
        return to;
    }

    public static RestaurantTo toDtoWithVotes(Restaurant restaurant, int votesCount) {
        RestaurantTo to = toDto(restaurant, votesCount);
        to.setVoteTos(convertVotes(restaurant.getVotes()));
        return to;
    }


    public static List<RestaurantTo> toDtos(List<Restaurant> restaurants, RestaurantService service){
        return restaurants.stream()
                .map(r -> RestaurantUtil.toDto(r, service.getVotesCount(r.id())))
                .collect(Collectors.toList());
    }
    public static List<RestaurantTo> toDtosWithMenus(List<Restaurant> restaurants, RestaurantService service){
        return restaurants.stream()
                .map(r -> RestaurantUtil.toDtoWithMenus(r, service.getVotesCount(r.id())))
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> toDtosWithVotes(List<Restaurant> restaurants, RestaurantService service){
        return restaurants.stream()
                .map(r -> RestaurantUtil.toDtoWithVotes(r, service.getVotesCount(r.id())))
                .collect(Collectors.toList());
    }


    private static List<MenuTo> convertMenus(List<Menu> menus){
        if(menus == null) return new ArrayList<>();
        return menus.stream()
                .map(MenuUtil::toDto)
                .collect(Collectors.toList());
    }

    private static Set<VoteTo> convertVotes(Set<Vote> votes){
        if(votes == null) return new HashSet<>();
        return votes.stream()
                .map(VoteUtil::toDtoWithUser)
                .collect(Collectors.toSet());
    }

    public static String replacePathVariable(String url, int restaurantId){
        return url.replaceAll("(\\{restaurantId})", String.valueOf(restaurantId));
    }


}
