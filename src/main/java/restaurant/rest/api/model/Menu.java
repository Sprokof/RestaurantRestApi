package restaurant.rest.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public Menu() {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MenuItem> menuItems;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public void addItem(MenuItem menuItem){
        if(this.menuItems == null) this.menuItems = new ArrayList<>();
        this.menuItems.add(menuItem);
        menuItem.setMenu(this);
    }

    public Menu(List<MenuItem> menuItems){
        this.menuItems = menuItems;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Menu(Menu menu){
        this.setDate(menu.date);
        this.setTime(menu.time);
        this.setMenuItems(menu.menuItems);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
