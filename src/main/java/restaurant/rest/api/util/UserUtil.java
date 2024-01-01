package restaurant.rest.api.util;

import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.User;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.to.MenuItemTo;
import restaurant.rest.api.to.RestaurantTo;
import restaurant.rest.api.to.UserTo;
import restaurant.rest.api.to.VoteTo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtil {
    public static UserTo toDto(User user){
        if(user == null) return null;
        UserTo userTo = new UserTo();
        userTo.setId(user.getId());
        userTo.setUsername(user.getUsername());
        userTo.setEmail(user.getEmail());
        userTo.setPassword(user.getPassword());
        userTo.setRegistered(user.getRegistered());
        userTo.setRoles(user.getRoles());
        userTo.setEnabled(user.isEnabled());
        userTo.setVotes(new HashSet<>());
        return userTo;
    }
    public static UserTo toDtoWithVotes(User user){
        UserTo userTo = toDto(user);
        userTo.setVotes(convertVotes(user.getVotes()));
        return userTo;
    }

    private static Set<VoteTo> convertVotes(Set<Vote> votes){
        if(votes == null) return new HashSet<>();
        return votes.stream()
                .map(VoteUtil::toDtoWithRestaurant)
                .collect(Collectors.toSet());
    }

    public static List<UserTo> toDtos(List<User> users){
        return users.stream()
                .map(UserUtil::toDto)
                .collect(Collectors.toList());
    }

    public static List<UserTo> toDtosWithVotes(List<User> users){
        return users.stream()
                .map(UserUtil::toDtoWithVotes)
                .collect(Collectors.toList());
    }
}

