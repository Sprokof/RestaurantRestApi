package restaurant.rest.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Setter
@Getter
public class Menu extends AbstractBaseEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    public Menu() {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
    @JoinColumn(name = "menu_id")
    private List<MenuItem> menuItems;

    @Setter
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    public void addItem(MenuItem menuItem){
        if(this.menuItems == null) this.menuItems = new ArrayList<>();
        this.menuItems.add(menuItem);
    }

    public void addItems(List<MenuItem> items){
        for(MenuItem item : items){
            addItem(item);
        }
    }

    public Menu(MenuItem ... menuItems){
        addItems(List.of(menuItems));
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
