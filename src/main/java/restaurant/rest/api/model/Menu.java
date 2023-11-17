package restaurant.rest.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu extends AbstractBaseEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "actual")
    private boolean actual;

    public Menu(){
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.actual = true;
    }

    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    @BatchSize(size = 5)
    @Setter
    private Set<MenuItem> menuItems;

    @ManyToOne()
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Menu{" +
                "date=" + date +
                ", time" + time +
                ", actual=" + actual +
                ", menuItems" + menuItems +
                ", restaurantId=" + restaurant.id()+
                '}';
    }
}
