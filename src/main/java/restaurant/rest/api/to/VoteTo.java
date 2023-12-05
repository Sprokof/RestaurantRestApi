package restaurant.rest.api.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class VoteTo extends AbstractBaseTo {
    private LocalDate voteDate;
    private LocalTime voteTime;

    @Override
    protected AbstractBaseEntity toEntity() {
        Vote vote = new Vote();
        vote.setVoteTime(this.getVoteTime());
        vote.setVoteDate(this.getVoteDate());
        return vote;
    }

    @Override
    protected AbstractBaseTo toDto(AbstractBaseEntity entity) {
        Vote vote = (Vote) entity;
        this.setVoteDate(vote.getVoteDate());
        this.setVoteTime(vote.getVoteTime());
        return this;
    }
}
