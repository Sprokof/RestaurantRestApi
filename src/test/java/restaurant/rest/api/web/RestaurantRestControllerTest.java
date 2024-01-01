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
import restaurant.rest.api.model.Restaurant;

import restaurant.rest.api.service.RestaurantService;
import restaurant.rest.api.to.RestaurantTo;
import restaurant.rest.api.util.RestaurantUtil;
import restaurant.rest.api.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static restaurant.rest.api.data.RestaurantTestData.*;
import static restaurant.rest.api.data.UserTestData.ADMIN_NAME;
import static restaurant.rest.api.data.UserTestData.USER_NAME;
import static restaurant.rest.api.web.RestaurantRestController.REST_ADMIN_URL;
import static restaurant.rest.api.web.RestaurantRestController.REST_URL;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestaurantRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private RestaurantService service;

    @Test
    @Order(1)
    @WithUserDetails(USER_NAME)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDto(RESTAURANT_1, DEFAULT_VOTES_COUNT)));
    }

    @Test
    @Order(2)
    @WithUserDetails(USER_NAME)
    void getWithVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID + "/with-votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDtoWithVotes(RESTAURANT_1, DEFAULT_VOTES_COUNT)));
    }

    @Test
    @Order(3)
    @WithUserDetails(USER_NAME)
    void getWithLastVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID + "/with-last-votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDtoWithVotes(RESTAURANT_1, DEFAULT_VOTES_COUNT)));
    }

    @Test
    @Order(4)
    @WithUserDetails(USER_NAME)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDtos(service.getAll(), service)));
    }

    @Test
    @Order(5)
    @WithUserDetails(USER_NAME)
    void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDtoWithMenus(RESTAURANT_1, DEFAULT_VOTES_COUNT)));

    }

    @Test
    @Order(6)
    @WithUserDetails(USER_NAME)
    void getAllByName() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-name?name=" + RESTAURANT_NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDtos(service.getAll(), service)));

    }

    @Test
    @Order(7)
    @WithUserDetails(USER_NAME)
    void getAllWithMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menu/by-date?date=" + LocalDate.now()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDtos(service.getAllWithMenuByDate(LocalDate.now()), service)));

    }

    @Test
    @Order(8)
    @WithUserDetails(USER_NAME)
    void getTop() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/top?top=2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDto(RESTAURANT_2, DEFAULT_VOTES_COUNT),
                        RestaurantUtil.toDto(RESTAURANT_1, DEFAULT_VOTES_COUNT)));
    }

    @Test
    @Order(9)
    @WithUserDetails(ADMIN_NAME)
    void getTopByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/top?top=2&date=" + LocalDate.now()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.toDto(RESTAURANT_2, DEFAULT_VOTES_COUNT),
                        RestaurantUtil.toDto(RESTAURANT_1, DEFAULT_VOTES_COUNT)));

    }

    @Test
    @Order(10)
    @WithUserDetails(ADMIN_NAME)
    void createWithLocation() throws Exception {
        RestaurantTo newRestaurant = getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        RestaurantTo created = RESTAURANT_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_TO_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_TO_MATCHER.assertMatch(RestaurantUtil.toDto(service.get(newId), DEFAULT_VOTES_COUNT), newRestaurant);
    }

    @Test
    @Order(11)
    @WithUserDetails(ADMIN_NAME)
    void update() throws Exception {
        RestaurantTo updated = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + "/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_TO_MATCHER.assertMatch(RestaurantUtil.toDto(service.get(RESTAURANT_ID), DEFAULT_VOTES_COUNT), updated);
    }

    @Test
    @Order(12)
    @WithUserDetails(ADMIN_NAME)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + "/" + RESTAURANT_4_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_4_ID));
    }



}
