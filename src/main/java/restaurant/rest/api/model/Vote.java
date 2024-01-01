package restaurant.rest.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote")
@Getter
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_date")
    @Setter
    private LocalDate voteDate;

    @Column(name = "vote_time")
    @Setter
    private LocalTime voteTime;

    public Vote(){
        this.voteDate = LocalDate.now();
        this.voteTime = LocalTime.now();
    }

    public Vote(Vote vote){
        this.setVoteDate(vote.voteDate);
        this.setVoteTime(vote.voteTime);

    }

    @Setter
    @JoinColumn(name = "user_id")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;


    @Setter
    @JoinColumn(name = "restaurant_id")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Vote{" +
                "voteDate=" + voteDate +
                ", voteTime=" + voteTime +
                '}';
    }

}
