package restaurant.rest.api.web;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import restaurant.rest.api.json.JsonUtil;
import restaurant.rest.api.model.User;
import restaurant.rest.api.service.UserService;
import restaurant.rest.api.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rest.api.data.UserTestData.*;
import static restaurant.rest.api.web.ProfileRestController.REST_URL;


@WithUserDetails(USER_NAME)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private UserService userService;


    @Test
    @Order(1)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(USER_1));
    }


    @Test
    @Order(2)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(USER_ID));

    }

    @Test
    // run separately because user deleted in prevision test
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userService.get(USER_ID), updated);
    }


}
