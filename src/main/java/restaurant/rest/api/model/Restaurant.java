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

    @Column(name = "count_votes")
    @Setter
    private int count_votes;

    public Restaurant(){}

    public Restaurant(String description, String name){
        this.description = description;
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "restaurant")
    private Set<Menu> menus;

}
