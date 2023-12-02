package restaurant.rest.api.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import restaurant.rest.api.model.*;
import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.service.UserService;
import restaurant.rest.api.service.VoteService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class PopulateDatabase implements CommandLineRunner  {

    private static final User USER_1 = new User("userName1", "user@email1.com", "userPassword1", Collections.singleton(Role.USER));
    private static final User USER_2 = new User("userName2", "user@email2.com", "userPassword2", Collections.singleton(Role.USER));
    private static final User ADMIN = new User("adminName", "admin@email.com", "userPassword", Collections.singleton(Role.ADMIN));
    private static final Restaurant RESTAURANT_1 = new Restaurant("restaurant1", "restaurant1Description");
    private static final Restaurant RESTAURANT_2 = new Restaurant("restaurant2", "restaurant2Description");
    private static final Restaurant RESTAURANT_3 = new Restaurant("restaurant3", "restaurant3Description");
    private static final Menu menu1 = new Menu(new MenuItem("dish1M1", 100), new MenuItem("dish2M1", 110));
    private static final Menu menu2 = new Menu(new MenuItem("dish1M2", 120), new MenuItem("dish2M2", 105));
    private static final Menu menu3 = new Menu(new MenuItem("dish1M3", 130), new MenuItem("dish2M3", 150));
    private static final Vote VOTE_1 = new Vote();
    private static final Vote VOTE_2 = new Vote();



    static {
        RESTAURANT_1.addMenu(menu1);
        RESTAURANT_2.addMenu(menu2);
        RESTAURANT_3.addMenu(menu3);

    }

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;


    public void insertUsers(){
        userService.create(USER_1);
        userService.create(USER_2);
        userService.create(ADMIN);
    }

    public void insertRestaurants(){
        restaurantService.create(RESTAURANT_1);
        restaurantService.create(RESTAURANT_2);
        restaurantService.create(RESTAURANT_3);
    }

    public void insertVotes(){
        voteService.create(VOTE_1, 1, 1);
        voteService.create(VOTE_2, 2, 2);
    }

    @Override
    public void run(String... args) throws Exception {
        insertUsers();
        insertRestaurants();
        insertVotes();
    }

}
