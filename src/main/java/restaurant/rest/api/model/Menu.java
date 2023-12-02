package restaurant.rest.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MenuItem> menuItems;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "restaurant_id")
    @Getter
    private Restaurant restaurant;


    public void addItem(MenuItem menuItem){
        if(this.menuItems == null) this.menuItems = new ArrayList<>();
        this.menuItems.add(menuItem);
        menuItem.setMenu(this);
    }

    public void addItems(List<MenuItem> items){
        for(MenuItem item : items){
            addItem(item);
            item.setMenu(this);
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
