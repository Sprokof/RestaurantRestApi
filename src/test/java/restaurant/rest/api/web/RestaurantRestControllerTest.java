package restaurant.rest.api.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import restaurant.rest.api.json.JsonUtil;
import restaurant.rest.api.model.Restaurant;

import restaurant.rest.api.service.RestaurantService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static restaurant.rest.api.data.RestaurantTestData.*;
import static restaurant.rest.api.data.UserTestData.*;
import static restaurant.rest.api.data.RestaurantTestData.getNew;
import static restaurant.rest.api.data.RestaurantTestData.getUpdated;
import static restaurant.rest.api.web.RestaurantRestController.REST_ADMIN_URL;
import static restaurant.rest.api.web.RestaurantRestController.REST_URL;

public class RestaurantRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private RestaurantService service;

    @Test
    @WithUserDetails(USERNAME)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_1));
    }

    @Test
    @WithUserDetails(USERNAME)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(service.getAll()));
    }

    @Test
    @WithUserDetails(USERNAME)
    void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_1));

    }

    @Test
    @WithUserDetails(USERNAME)
    void getAllByName() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-name?name=" + RESTAURANT_NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_3, RESTAURANT_2, RESTAURANT_1));
    }

    @Test
    @WithUserDetails(USERNAME)
    void getAllWithMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menu/by-date?date=" + MENU_CREATED_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_3, RESTAURANT_2, RESTAURANT_1));
    }

    @Test
    @WithUserDetails(USERNAME)
    void getTop() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/top?top=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_2));

    }

    @Test
    @WithUserDetails(ADMIN_USERNAME)
    void getTopByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/top?top=2&date=" + MENU_CREATED_DATE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_2, RESTAURANT_1));

    }

    @Test
    @WithUserDetails(ADMIN_USERNAME)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(ADMIN_USERNAME)
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + "/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID), updated);
    }



}
