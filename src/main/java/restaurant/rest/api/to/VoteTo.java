package restaurant.rest.api.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Vote;
import restaurant.rest.api.util.RestaurantUtil;
import restaurant.rest.api.util.UserUtil;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Schema(description = "Vote's entity")
public class VoteTo extends AbstractBaseTo {
    private LocalDate voteDate;
    private LocalTime voteTime;
    private UserTo userTo;
    private RestaurantTo restaurantTo;


    @Override
    public Vote toEntity() {
        Vote vote = new Vote();
        vote.setId(this.getId());
        if(this.voteDate != null) vote.setVoteDate(this.getVoteDate());
        if(this.voteTime != null) vote.setVoteTime(this.getVoteTime());
        return vote;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + getId() + ", " +
                "voteDate=" + voteDate +
                ", voteTime=" + voteTime ;
    }
}
