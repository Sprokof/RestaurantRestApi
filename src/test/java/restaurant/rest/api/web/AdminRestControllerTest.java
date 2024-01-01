package restaurant.rest.api.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import restaurant.rest.api.json.JsonUtil;
import restaurant.rest.api.model.User;
import restaurant.rest.api.service.UserService;
import restaurant.rest.api.to.UserTo;
import restaurant.rest.api.util.UserUtil;
import restaurant.rest.api.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rest.api.data.UserTestData.*;

@WithUserDetails(value = ADMIN_NAME)
public class AdminRestControllerTest extends AbstractRestControllerTest {

    static String REST_URL = AdminRestController.REST_URL;

    @Autowired
    private UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.toDto(ADMIN)));
    }

    @Test
    void getWithVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + ADMIN_ID + "/with-votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.toDtoWithVotes(ADMIN)));
    }

    @Test
    void getWithLastVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + ADMIN_ID + "/with-last-vote"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.toDtoWithVotes(ADMIN)));
    }


    @Test
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-email?email=" + ADMIN.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.toDto(ADMIN)));
    }

    @Test
    void getByUsername() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-username?username=" + ADMIN.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.toDto(ADMIN)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + USER_2_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(USER_2_ID));
    }

    @Test
    @WithMockUser
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        UserTo updated = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        USER_TO_MATCHER.assertMatch(UserUtil.toDto(userService.get(USER_ID)), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        UserTo newUser = getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andExpect(status().isCreated());

        UserTo created = USER_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_TO_MATCHER.assertMatch(created, newUser);
        USER_TO_MATCHER.assertMatch(UserUtil.toDto(userService.get(newId)), newUser);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.toDtos(userService.getAll())));
    }




}
