package restaurant.rest.api.model;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "description")
    @Setter
    private String description;

    @Column(name = "restaurant_name")
    @Setter
    private String name;

    public Restaurant(){}

    public Restaurant(String description, String name){
        this.description = description;
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.REFRESH)
    private Set<Menu> menus;

    @OneToMany(cascade = CascadeType.REFRESH)
    private Set<Vote> votes;

}
