package messaging.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import messaging.converters.UserMapper;
import messaging.dtos.UserDTO;
import messaging.exceptions.RestUsernameAlreadyExistsException;
import messaging.models.user.User;
import messaging.repository.UserRepository;
import messaging.utils.URLConstants;
import messaging.utils.UserPatchUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserPatchUtil userPatchUtil;


    @InjectMocks
    private UserServiceImpl userService;

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
    void itShouldSaveUserSuccessFully() {

        given(userMapper.toEntity(userDTO)).willReturn(user);
        given(userRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.create(userDTO);

        assertThat(savedUser).isNotNull();

        verify(userRepository).save(any(User.class));

    }

    @Test
    void shouldThrowErrorWhenSaveUserWithExistingEmail() {



        given(userMapper.toEntity(userDTO)).willReturn(user);
        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        assertThrows(RestUsernameAlreadyExistsException.class,() -> {
            userService.create(userDTO);
        });

        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    public void itShouldUpdatePassword() throws Exception {
        //Given
        final String newPassword = "newPassword";
        final String patchString = "[{ \"op\": \"replace\", \"path\": \"/password\", \"value\": \"" + newPassword + "\" }]";
        final JsonPatch patch = JsonPatch.fromJson(new ObjectMapper().readTree(patchString));


        User updatedUser = user.toBuilder().password(newPassword).build();

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.save(updatedUser)).willReturn(updatedUser);// When
        given(userPatchUtil.applyPatchToUser(patch, user)).willReturn(updatedUser);// When

        User expected = userService.update(user.getId(), patch);

        // Then

        assertThat(expected).isNotNull();
        assertThat(expected.getPassword()).isEqualTo(newPassword);
        assertThat(user.getPassword()).isNotEqualTo(newPassword);

    }

    @Test
    public void itShouldReturnUserWhenUserIdGiven(){

        given(userRepository.findById(userID)).willReturn(Optional.of(user));

        final Optional<User> expected  = userService.getByID(userID);

        assertThat(expected).isNotNull();

    }

    @Test
    public void itShouldDeleteUser() {

        userService.delete(userID);

        verify(userRepository, times(1)).deleteById(userID);
    }

}
