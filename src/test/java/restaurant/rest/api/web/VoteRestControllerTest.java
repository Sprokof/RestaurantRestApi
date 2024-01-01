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
import restaurant.rest.api.service.VoteService;
import restaurant.rest.api.to.VoteTo;
import restaurant.rest.api.util.RestaurantUtil;
import restaurant.rest.api.util.VoteUtil;
import restaurant.rest.api.util.exception.NotFoundException;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_2_ID;
import static restaurant.rest.api.data.RestaurantTestData.RESTAURANT_ID;
import static restaurant.rest.api.data.UserTestData.USER_NAME;
import static restaurant.rest.api.data.UserTestData.USER_NAME_2;
import static restaurant.rest.api.data.UserTestData.USER_ID;
import static restaurant.rest.api.data.VoteTestData.*;
import static restaurant.rest.api.web.VoteRestController.PROFILE_REST_URL;
import static restaurant.rest.api.web.VoteRestController.RESTAURANT_REST_URL;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoteRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private VoteService service;

    @Test
    @Order(1)
    @WithUserDetails(USER_NAME)
    void getByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL + "/" + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.toDto(VOTE_1)));
    }

    @Test
    @Order(2)
    @WithUserDetails(USER_NAME)
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.toDtos(List.of(VOTE_1))));
    }

    @Test
    @Order(3)
    @WithUserDetails(USER_NAME)
    void getAllByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantUtil.replacePathVariable(RESTAURANT_REST_URL, RESTAURANT_2_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.toDtos(List.of(VOTE_2, VOTE_3))));
    }

    @Test
    @Order(3)
    @WithUserDetails(USER_NAME)
    void getAllLastByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantUtil.replacePathVariable(RESTAURANT_REST_URL, RESTAURANT_2_ID) + "/last"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.toDtos(List.of(VOTE_2, VOTE_3))));
    }



    @Test
    @Order(4)
    @WithUserDetails(USER_NAME)
    void getByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantUtil.replacePathVariable(RESTAURANT_REST_URL, RESTAURANT_2_ID) + "/" + VOTE_2_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.toDto(VOTE_2)));
    }


    @Test
    @Order(5)
    @WithUserDetails(USER_NAME)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(RestaurantUtil.replacePathVariable(PROFILE_REST_URL, RESTAURANT_2_ID) + "/" + VOTE_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.getByUserId(VOTE_ID, USER_ID));
    }


    @Test
    @Order(6)
    @WithUserDetails(USER_NAME)
    void createWithLocation() throws Exception {
        VoteTo newTo = getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post((RestaurantUtil.replacePathVariable(RESTAURANT_REST_URL, RESTAURANT_ID)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newTo.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newTo);
        VOTE_TO_MATCHER.assertMatch(VoteUtil.toDto(service.getByUserId(newId,  RESTAURANT_ID)), newTo);
    }


    @Test
    @Order(7)
    @WithUserDetails(USER_NAME_2)
    void update() throws Exception {
        VoteTo updatedTo = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(RestaurantUtil.replacePathVariable(RESTAURANT_REST_URL, RESTAURANT_2_ID) + "/" + VOTE_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent());

        VOTE_TO_MATCHER.assertMatch(VoteUtil.toDto(service.getByRestaurantId(VOTE_2_ID, RESTAURANT_2_ID)), updatedTo);
    }



}
