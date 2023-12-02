package restaurant.rest.api.data;

import restaurant.rest.api.matcher.MatcherFactory;
import restaurant.rest.api.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("voteDate", "voteTime", "restaurant", "user");
    public static final int VOTE_ID = 1;
    public static final int NOT_FOUND = 0;
    public static final int CREATED_VOTE_ID = 3;
    public static final Vote VOTE_1 = new Vote();
    public static final Vote VOTE_2 = new Vote();

    static {
        VOTE_1.setId(VOTE_ID);
        VOTE_2.setId(VOTE_ID + 1);
    }

    public static Vote getNew() {
        return new Vote();
    }

    public static Vote getUpdated(){
        Vote updated = new Vote(VOTE_2);
        updated.setId(VOTE_2.id());
        updated.setVoteDate(LocalDate.now());
        updated.setVoteTime(LocalTime.now());
        return updated;
    }


}
