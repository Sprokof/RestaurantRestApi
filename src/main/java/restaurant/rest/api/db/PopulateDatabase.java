package restaurant.rest.api.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import restaurant.rest.api.model.*;
import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.service.UserService;
import restaurant.rest.api.service.VoteService;

import java.util.List;

@Component
public class PopulateDatabase implements CommandLineRunner  {

    private static final User USER_1 = new User("userName1", "user@email1.com", "userPassword1", Role.USER);
    private static final User USER_2 = new User("userName2", "user@email2.com", "userPassword2", Role.USER);
    private static final User ADMIN = new User("adminName", "admin@email.com", "userPassword", Role.ADMIN);
    private static final Restaurant RESTAURANT_1 = new Restaurant("restaurant1", "restaurant1Desc");
    private static final Restaurant RESTAURANT_2 = new Restaurant("restaurant2", "restaurant2Desc");
    private static final Restaurant RESTAURANT_3 = new Restaurant("restaurant3", "restaurant3Desc");
    private static final Menu menu1 = new Menu();
    private static final Menu menu2 = new Menu();
    private static final Menu menu3 = new Menu();



    static {
        USER_1.addRole(Role.USER);
        USER_2.addRole(Role.USER);
        ADMIN.addRole(Role.ADMIN);

        addMenuItems(menu1, List.of(new MenuItem("dish1M1", 100), new MenuItem("dish2M1", 110)));
        addMenuItems(menu2, List.of(new MenuItem("dish1M2", 120), new MenuItem("dish2M2", 105)));
        addMenuItems(menu3, List.of(new MenuItem("dish1M3", 130), new MenuItem("dish2M3", 150)));

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
        voteService.create(new Vote(), 1, 2);
        voteService.create(new Vote(), 2, 1);
        this.restaurantService.updateVotesCount(1);
        this.restaurantService.updateVotesCount(2);
    }

    @Override
    public void run(String... args) throws Exception {
        insertUsers();
        insertRestaurants();
        insertVotes();
    }

    private static void addMenuItems(Menu menu, List<MenuItem> items){
        items.forEach(menu::addItem);
    }
}
