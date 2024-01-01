package restaurant.rest.api.util;

import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.to.AbstractBaseTo;
import restaurant.rest.api.to.MenuTo;
import restaurant.rest.api.to.VoteTo;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VoteUtil {
    private final static LocalTime ACCEPT_TIME_LIMIT = LocalTime.of(11, 0);
    public static boolean accept(LocalTime voteTime){
        return voteTime.isBefore(ACCEPT_TIME_LIMIT);
    }

    public static VoteTo toDto(Vote vote) {
        VoteTo voteTo = new VoteTo();
        voteTo.setId(vote.getId());
        voteTo.setVoteDate(vote.getVoteDate());
        voteTo.setVoteTime(vote.getVoteTime());
        return voteTo;
    }

    public static VoteTo toDtoWithUser(Vote vote){
        VoteTo voteTo = toDto(vote);
        voteTo.setUserTo(UserUtil.toDto(vote.getUser()));
        return voteTo;
    }

    public static VoteTo toDtoWithRestaurant(Vote vote){
        VoteTo voteTo = toDto(vote);
        voteTo.setRestaurantTo(RestaurantUtil.toDto(vote.getRestaurant(), 0));
        return voteTo;
    }

    public static Set<VoteTo> toDtos(List<Vote> votes){
        return votes.stream()
                .map(VoteUtil::toDto)
                .collect(Collectors.toSet());
    }

    public static Set<VoteTo> toDtosWithUser(List<Vote> votes){
        return votes.stream()
                .map(VoteUtil::toDtoWithUser)
                .collect(Collectors.toSet());
    }

    public static Set<VoteTo> toDtosWithRestaurant(List<Vote> votes){
        return votes.stream()
                .map(VoteUtil::toDtoWithRestaurant)
                .collect(Collectors.toSet());
    }




}
