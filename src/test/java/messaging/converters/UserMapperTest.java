package messaging.converters;

import messaging.controllers.UserController;
import messaging.dtos.UserDTO;
import messaging.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {


    @Autowired
    private UserMapper userMapper;

    private UserDTO userDTO;
    private User user;
    private Long userID;

    @BeforeEach
    public void setup() {

        userMapper = new UserMapperImpl();

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
    public void shouldMapEntityToDto(){

        UserDTO mappedDTO = userMapper.toDTO(user);

        assertThat(mappedDTO).hasFieldOrPropertyWithValue("username", user.getUsername());
       //Sensitive info
        assertThat(mappedDTO.getPassword()).isNull();
    }

    @Test
    public void shouldMapDtoToEntity(){

        User mappedEntity = userMapper.toEntity(userDTO);

        assertThat(mappedEntity).hasFieldOrPropertyWithValue("username", userDTO.getUsername());
        assertThat(mappedEntity).hasFieldOrPropertyWithValue("password", userDTO.getPassword());

    }
}
