package messaging.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import javax.json.Json;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void itShouldReturn400WhenCreatingUserWithoutEmail() throws Exception {
        // Given
        doReturn(user).when(userService).create(any(UserDTO.class));

        userDTO = UserDTO.builder()
                .username(null)
                .password("password").build();


        // When
        final ResultActions result = mockMvc.perform(
                post(URLConstants.USERS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO))
                        .accept(MediaType.APPLICATION_JSON));


        // Then
        result.andExpect(status().isBadRequest());
    }


    @Test
    public void itShouldReturn400WhenCreatingUserWithoutPassword() throws Exception {
        // Given
        doReturn(user).when(userService).create(any(UserDTO.class));

        userDTO = UserDTO.builder()
                .username("username")
                .password(null).build();

        // When
        final ResultActions result = mockMvc.perform(
                post(URLConstants.USERS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO))
                        .accept(MediaType.APPLICATION_JSON));


        // Then
        result.andExpect(status().isBadRequest());
    }
    @Test
    public void itShouldReturnNoContentAndDeleteUserGivenUserId() throws Exception {
        // Given

        doNothing().when(userService).delete(userID);
        // When
        final ResultActions result = mockMvc.perform(
                delete(String.format("%s/%d", URLConstants.USERS_BASE_URL, userID)));


        // Then
        result.andExpect(status().isNoContent());

    }

    @Test
    public void itShouldReturnOneUserGivenUserID() throws Exception {
        //Given
        doReturn(Optional.of(user)).when(userService).getByID(userID);

        // When
        final ResultActions result = mockMvc.perform(
                get(String.format("%s/%d", URLConstants.USERS_BASE_URL, userID)));


        // Then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.username", equalTo(user.getUsername())));
        result.andExpect(jsonPath("$.password").doesNotExist());


    }


    @Test
    public void itShouldReturnRestResourceNotFoundExceptionWhenUserIDDoesNotExists() throws Exception {
        //Given
        final Long missingUserID = 0L;
        doReturn(Optional.empty()).when(userService).getByID(missingUserID);

        // When
        final ResultActions result = mockMvc.perform(
                get(String.format("%s/%d", URLConstants.USERS_BASE_URL, missingUserID)));


        // Then
        result.andExpect(status().isNotFound());

    }

    @Test
    public void itShouldUpdatePassword() throws Exception {
        //Given
        final String newPassword = "newPassword";
        final String patchString = "[{ \"op\": \"replace\", \"path\": \"/password\", \"value\": \"" + newPassword + "\" }]";
        final JsonPatch patch = JsonPatch.fromJson(mapper.readTree(patchString));


        User updatedUser = user.toBuilder().password(newPassword).build();

        doReturn(updatedUser).when(userService).update(userID, patch);

        // When
        final ResultActions result = mockMvc.perform(
                patch(String.format("%s/%d", URLConstants.USERS_BASE_URL, userID))
                        .contentType("application/json-patch+json")
        .content(patchString));


        // Then
        result.andExpect(status().isOk());

    }


}
