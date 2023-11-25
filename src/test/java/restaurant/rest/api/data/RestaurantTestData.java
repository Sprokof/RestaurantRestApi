package restaurant.rest.api.data;

import restaurant.rest.api.matcher.MatcherFactory;
import restaurant.rest.api.model.Restaurant;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;

import java.time.LocalDateTime;
import java.util.Collections;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("menus", "countVotes");

    public static final int RESTAURANT_ID = 1;
    public static final int NOT_FOUND = 0;
    public static final int CREATED_RESTAURANT_ID = 4;
    public static final Restaurant RESTAURANT_1 = new Restaurant("restaurant1", "restaurant1Description");
    public static final Restaurant RESTAURANT_2 = new Restaurant("restaurant2", "restaurant2Description");
    public static final Restaurant RESTAURANT_3 = new Restaurant("restaurant3", "restaurant3Description");

    static {
        RESTAURANT_1.setId(RESTAURANT_ID);
        RESTAURANT_2.setId(RESTAURANT_ID + 1);
        RESTAURANT_3.setId(RESTAURANT_ID + 2);
    }
    public static Restaurant getNew() {
        return new Restaurant("NewRestaurant", "NewRestaurantDescription");
    }

    public static Restaurant getUpdated(){
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setId(RESTAURANT_ID);
        updated.setName("UpdatedRestaurant");
        updated.setDescription("UpdatedRestaurantDescription");
        return updated;
    }



}
