package restaurant.rest.api.model;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "description", nullable = false)
    @Setter
    @Size(min = 20, max = 200)
    @NotBlank
    private String description;

    @Column(name = "restaurant_name", nullable = false)
    @Setter
    @Size(min = 10, max = 20)
    @NotBlank
    private String name;


    public Restaurant(){}

    public Restaurant(String name, String description){
        this.name = name;
        this.description = description;

    }
    public Restaurant(Restaurant restaurant){
        this(restaurant.name, restaurant.description);
        this.setMenus(restaurant.menus);
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id")
    private List<Menu> menus;


    public void addMenu(Menu menu){
        if(this.menus == null) this.menus = new ArrayList<>();
        this.menus.add(menu);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
