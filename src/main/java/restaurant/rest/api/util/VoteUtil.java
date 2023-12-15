package restaurant.rest.api.util;

import restaurant.rest.api.model.Vote;
import restaurant.rest.api.to.VoteTo;

import java.time.LocalTime;

public class VoteUtil {
    private final static LocalTime ACCEPT_TIME_LIMIT = LocalTime.of(11, 0);
    public static boolean accept(LocalTime voteTime){
        return voteTime.isBefore(ACCEPT_TIME_LIMIT);
    }

}
