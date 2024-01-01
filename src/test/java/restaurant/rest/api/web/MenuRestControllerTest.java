package restaurant.rest.api.web;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import restaurant.rest.api.json.JsonUtil;
import restaurant.rest.api.model.Menu;
import restaurant.rest.api.service.MenuService;
import restaurant.rest.api.to.MenuTo;
import restaurant.rest.api.util.MenuUtil;
import restaurant.rest.api.util.RestaurantUtil;
import restaurant.rest.api.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rest.api.data.MenuTestData.*;
import static restaurant.rest.api.data.MenuTestData.getUpdated;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_ID;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_4_ID;
import static restaurant.rest.api.data.RestaurantTestData.MENU_CREATED_DATE;
import static restaurant.rest.api.data.UserTestData.ADMIN_NAME;
import static restaurant.rest.api.data.UserTestData.USER_NAME;
import static restaurant.rest.api.web.MenuRestController.REST_ADMIN_URL;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuRestControllerTest extends AbstractRestControllerTest {

    static String REST_URL = RestaurantUtil.replacePathVariable(MenuRestController.REST_URL, RESTAURANT_ID);

    @Autowired
    private MenuService service;

    @Test
    @Order(1)
    @WithUserDetails(ADMIN_NAME)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantUtil.replacePathVariable(REST_ADMIN_URL, RESTAURANT_ID) + "/" + MENU_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuUtil.toDto(MENU_1)));
    }

    @Test
    @Order(2)
    @WithUserDetails(ADMIN_NAME)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantUtil.replacePathVariable(REST_ADMIN_URL, RESTAURANT_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(MenuUtil.toDto(MENU_1))));
    }

    @Test
    @Order(7)
    @WithUserDetails(ADMIN_NAME)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(RestaurantUtil.replacePathVariable(REST_ADMIN_URL, RESTAURANT_ID) + "/" + MENU_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(MENU_ID, RESTAURANT_ID));
    }

    @Test
    @Order(3)
    @WithUserDetails(ADMIN_NAME)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantUtil.replacePathVariable(REST_ADMIN_URL, RESTAURANT_ID) + "/by_date?date=" + MENU_CREATED_DATE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuUtil.toDto(MENU_1)));
    }

    @Test
    @Order(6)
    @WithUserDetails(ADMIN_NAME)
    void createWithLocation() throws Exception {
        MenuTo newMenu = getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(RestaurantUtil.replacePathVariable(REST_ADMIN_URL, RESTAURANT_4_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isCreated());

        MenuTo created = MENU_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_TO_MATCHER.assertMatch(created, newMenu);
        MENU_TO_MATCHER.assertMatch(MenuUtil.toDto(service.get(newId, RESTAURANT_4_ID)), newMenu);
    }

    @Test
    @Order(5)
    @WithUserDetails(ADMIN_NAME)
    void update() throws Exception {
        MenuTo updated = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(RestaurantUtil.replacePathVariable(REST_ADMIN_URL, RESTAURANT_ID) + "/" + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER.assertMatch(MenuUtil.toDto(service.get(MENU_ID, RESTAURANT_ID)), updated);
    }


    @Test
    @Order(4)
    @WithUserDetails(USER_NAME)
    void getLast() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuUtil.toDto(MENU_1)));
    }




}
