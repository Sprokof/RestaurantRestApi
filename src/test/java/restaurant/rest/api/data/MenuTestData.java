package restaurant.rest.api.data;

import restaurant.rest.api.matcher.MatcherFactory;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurantId", "time", "menuItems", "actual");
    public static final int MENU_ID = 1;
    public static final int CREATED_MENU_ID = 4;
    public static final int NOT_FOUND = 0;
    public static final Menu MENU_1 = new Menu(new MenuItem("dish1M1", 100), new MenuItem("dish2M1", 110));
    public static final Menu MENU_2 = new Menu(new MenuItem("dish1M2", 120), new MenuItem("dish2M2", 105));
    public static final Menu MENU_3 = new Menu(new MenuItem("dish1M3", 130), new MenuItem("dish2M3", 150));


    static {
        MENU_1.setId(MENU_ID);
        MENU_2.setId(MENU_ID + 1);
        MENU_3.setId(MENU_ID + 2);
    }
    public static Menu getNew(){
        return new Menu(new MenuItem("newMenuDish1", 150), new MenuItem("newMenuDish2", 70));
    }

    public static Menu getUpdated() {
        Menu updated = new Menu(MENU_1);
        updated.setDate(LocalDate.now());
        updated.setId(MENU_ID);
        updated.addItem(new MenuItem("updatedMenuDish", 130));
        return updated;
    }



}
