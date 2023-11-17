package restaurant.rest.api.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote")
@Getter
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_date")
    private final LocalDate voteDate;

    @Column(name = "voteTime")
    private final LocalTime voteTime;

    @Setter
    @Column(name = "actual")
    private boolean actual;

    public Vote(){
        this.voteDate = LocalDate.now();
        this.voteTime = LocalTime.now();
        this.actual = true;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
