package restaurant.rest.api.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "restaurant")
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

    @Column(name = "count_votes")
    @Setter
    private int countVotes;

    public Restaurant(){

    }

    public Restaurant(String name, String description){
        this.name = name;
        this.description = description;

    }

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, mappedBy = "restaurant", fetch = FetchType.LAZY)
    @Setter
    private Set<Menu> menus;

    public void addMenu(Menu menu){
        if(this.menus == null) this.menus = new HashSet<>();
        this.menus.add(menu);
        menu.setRestaurant(this);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", countVotes=" + countVotes + '\'' +
                '}';
    }
}
