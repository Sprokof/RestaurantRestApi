package restaurant.rest.api.model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @Column(name = "menu_id")
    private Integer menuId;

    public MenuItem(){
    }

    public MenuItem(String dish, double price){
        this.dish = dish;
        this.price = price;
    }


    @Override
    public String toString() {
        return "MenuItem{" +
                "dish='" + dish + '\'' +
                ", price=" + price +
                '}';
    }

}
