package restaurant.rest.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu extends AbstractBaseEntity {

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Column(name = "actual")
    private boolean actual;

    public Menu(){
        this.dateTime = LocalDateTime.now();
        this.actual = true;
    }

    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    private Set<MenuItem> menuItems;

    @ManyToOne()
    private Restaurant restaurant;

}
