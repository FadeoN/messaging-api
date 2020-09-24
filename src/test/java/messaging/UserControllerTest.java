package messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import messaging.controllers.UserController;
import messaging.converters.UserMapperImpl;
import messaging.dtos.UserDTO;
import messaging.models.user.User;
import messaging.services.UserService;
import messaging.utils.URLConstants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @MockBean  private UserService userService;

    private UserDTO userDTO;
    private User user;
    private Long userID;

    @BeforeEach
    public void setup() {

        userID = 1L;

        userDTO = UserDTO.builder()
                .username("username")
                .password("password").build();

        user = User.builder()
                .id(userID)
                .username("username")
                .password("password").build();

    }

    @Test
    public void itShouldReturnOkWhenUserCreated() throws Exception {
        // Given
        doReturn(user).when(userService).create(any(UserDTO.class));

        // When
        final ResultActions result = mockMvc.perform(
                post(URLConstants.USERS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO))
                .accept(MediaType.APPLICATION_JSON));


        // Then
        result.andExpect(status().isCreated());
        result.andExpect(redirectedUrlPattern(String.format("%s/*", URLConstants.USERS_BASE_URL)));
    }

    @Test
    public void itShouldReturnNoContentAndDeleteUserGivenUserId() throws Exception {
        // Given

        // When
        final ResultActions result = mockMvc.perform(
                delete(String.format("%s/%d", URLConstants.USERS_BASE_URL, userID)));


        // Then
        result.andExpect(status().isNoContent());
    }

}
