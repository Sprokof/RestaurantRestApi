package restaurant.rest.api.data;

import restaurant.rest.api.matcher.MatcherFactory;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.to.VoteTo;
import restaurant.rest.api.util.VoteUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class,"voteDate", "voteTime", "restaurant", "user");
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class,"voteDate", "voteTime", "restaurantTo", "userTo");
    public static final int VOTE_ID = 1, VOTE_2_ID = 2, VOTE_3_ID = 3;
    public static final int NOT_FOUND = 0;
    public static final int CREATED_VOTE_ID = 4;
    public static final Vote VOTE_1 = new Vote();
    public static final Vote VOTE_2 = new Vote();
    public static final Vote VOTE_3 = new Vote();


    static {
        VOTE_1.setId(VOTE_ID);
        VOTE_2.setId(VOTE_ID + 1);
        VOTE_3.setId(VOTE_ID + 2);
    }

    public static Vote getNew() {
        return new Vote();
    }

    public static VoteTo getNewTo(){
        return VoteUtil.toDto(new Vote());
    }

    public static Vote getUpdated(){
        Vote updated = new Vote(VOTE_2);
        updated.setId(VOTE_2.id());
        updated.setVoteDate(LocalDate.now());
        updated.setVoteTime(LocalTime.now());
        return updated;
    }

    public static VoteTo getUpdatedTo(){
        VoteTo to = VoteUtil.toDto(getUpdated());
        to.setVoteTime(LocalTime.of(10, 0));
        return to;
    }


}
