package restaurant.rest.api.model;


import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
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

    @OneToMany
    private Set<Menu> menus;

}
