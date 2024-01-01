package restaurant.rest.api.data;

import restaurant.rest.api.matcher.MatcherFactory;
import restaurant.rest.api.model.*;
import restaurant.rest.api.to.RestaurantTo;
import restaurant.rest.api.util.RestaurantUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus", "votes");

    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class, "countVotes", "menuTos", "voteTos");

    public static final LocalDate MENU_CREATED_DATE = LocalDate.now();

    public static final String RESTAURANT_NAME = "restaurant";
    public static final int RESTAURANT_ID = 1, RESTAURANT_2_ID = 2, RESTAURANT_3_ID = 3,
            RESTAURANT_4_ID = 4, DEFAULT_VOTES_COUNT = 0;
    public static final int NOT_FOUND = 0;
    public static final int CREATED_RESTAURANT_ID = 5;
    public static final int TOP = 2;
    public static final Restaurant RESTAURANT_1 = new Restaurant("restaurant1", "restaurant1Description");
    public static final Restaurant RESTAURANT_2 = new Restaurant("restaurant2", "restaurant2Description");
    public static final Restaurant RESTAURANT_3 = new Restaurant("restaurant3", "restaurant3Description");

    public static final Restaurant RESTAURANT_4 = new Restaurant("restaurant4", "restaurant4Description");


    static {
        RESTAURANT_1.setId(RESTAURANT_ID);
        RESTAURANT_2.setId(RESTAURANT_2_ID);
        RESTAURANT_3.setId(RESTAURANT_3_ID);
        RESTAURANT_4.setId(RESTAURANT_4_ID);
    }
    public static Restaurant getNew() {
        Restaurant restaurant = new Restaurant("newRestaurant", "newRestaurantDescription");
        Menu newMenu = new Menu(new MenuItem("newMenuItem1", 200), new MenuItem("newMenuItem1", 230));
        restaurant.addMenu(newMenu);
        return restaurant;
    }

    public static RestaurantTo getNewTo() {
        Restaurant restaurant = new Restaurant("newRestaurant", "newRestaurantDescription");
        Menu newMenu = new Menu(new MenuItem("newMenuItem1", 200), new MenuItem("newMenuItem1", 230));
        restaurant.addMenu(newMenu);
        return RestaurantUtil.toDtoWithMenus(restaurant, DEFAULT_VOTES_COUNT);
    }

    public static Restaurant getUpdated(){
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setId(RESTAURANT_ID);
        updated.setName("updatedRestaurant");
        updated.setDescription("updatedRestaurantDescription");
        return updated;
    }

    public static RestaurantTo getUpdatedTo(){
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setId(RESTAURANT_ID);
        updated.setName("updatedRestaurant");
        updated.setDescription("updatedRestaurantDescription");
        return RestaurantUtil.toDto(updated, DEFAULT_VOTES_COUNT);
    }



}
