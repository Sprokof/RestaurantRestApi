package restaurant.rest.api.util;

import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.model.User;
import restaurant.rest.api.to.MenuItemTo;
import restaurant.rest.api.to.MenuTo;
import restaurant.rest.api.to.UserTo;

import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {

    public static MenuTo toDto(Menu menu){
        MenuTo menuTo = new MenuTo();
        menuTo.setId(menu.getId());
        menuTo.setDate(menu.getDate());
        menuTo.setTime(menu.getTime());
        menuTo.setItems(convertMenuItem(menu.getMenuItems()));
        return menuTo;
    }

    private static List<MenuItemTo> convertMenuItem(List<MenuItem> menuItems){
        return menuItems.stream()
                .map(MenuItemUtil :: toDto)
                .collect(Collectors.toList());
    }
}
