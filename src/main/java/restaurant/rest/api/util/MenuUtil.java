package restaurant.rest.api.util;

import restaurant.rest.api.model.Menu;
import restaurant.rest.api.model.MenuItem;
import restaurant.rest.api.model.User;
import restaurant.rest.api.to.MenuItemTo;
import restaurant.rest.api.to.MenuTo;
import restaurant.rest.api.to.UserTo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {

    public static MenuTo toDto(Menu menu){
        MenuTo menuTo = new MenuTo();
        menuTo.setId(menu.getId());
        menuTo.setDate(menu.getDate());
        menuTo.setTime(menu.getTime());
        menuTo.setMenuItemTos(convertMenuItem(menu.getMenuItems()));
        return menuTo;
    }

    public static List<MenuTo> toDtos(List<Menu> menus){
        return menus.stream()
                .map(m -> toDto(m))
                .collect(Collectors.toList());

    }

    private static List<MenuItemTo> convertMenuItem(List<MenuItem> menuItems){
        if(menuItems == null) return new ArrayList<>();
        return menuItems.stream()
                .map(MenuItemUtil :: toDto)
                .collect(Collectors.toList());
    }


}
