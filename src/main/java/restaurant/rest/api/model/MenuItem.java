package restaurant.rest.api.model;


import javax.persistence.Column;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "menu_item")
@Getter
public class MenuItem extends AbstractBaseEntity {

    @Column(name = "dish")
    @Setter
    private String dish;

    @Column(name = "price")
    @Setter
    private double price;


    public MenuItem(){
    }

    public MenuItem(String dish, double price){
        this.dish = dish;
        this.price = price;
    }

    @ManyToOne()
    @JoinColumn(name = "menu_id")
    @Setter
    private Menu menu;

    @Override
    public String toString() {
        return "MenuItem{" +
                "dish='" + dish + '\'' +
                ", price=" + price +
                ", menuId=" + menu.id() +
                '}';
    }
}
