package restaurant.rest.api.model;

import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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

    @Setter
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @Setter
    @JoinColumn(name = "restaurant_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Vote{" +
                "voteDate=" + voteDate +
                ", voteTime=" + voteTime +
                ", actual=" + actual +
                ", userId=" + user.id() +
                ", restaurantId=" + restaurant.id() +
                '}';
    }

}
