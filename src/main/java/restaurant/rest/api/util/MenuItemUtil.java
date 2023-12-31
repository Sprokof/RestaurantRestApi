package restaurant.rest.api.util;

import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.to.MenuItemTo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MenuItemUtil {
    public static MenuItemTo toDto(MenuItem menuItem){
        MenuItemTo itemTo = new MenuItemTo();
        itemTo.setId(menuItem.getId());
        itemTo.setDish(menuItem.getDish());
        itemTo.setPrice(menuItem.getPrice());
        return itemTo;
    }

    public static Set<MenuItemTo> toDtos(Set<MenuItem> menuItems){
        return menuItems.stream()
                .map(MenuItemUtil::toDto)
                .collect(Collectors.toSet());
    }




}
