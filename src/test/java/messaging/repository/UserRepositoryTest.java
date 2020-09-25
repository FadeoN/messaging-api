package messaging.repository;

import messaging.models.user.User;
import messaging.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Test
    public void shouldSaveAndFetchUserByUsername(){

        user = User.builder()
                .username("username")
                .password("password").build();

        userRepository.save(user);
        assertTrue(userRepository.findByUsername("username").isPresent());
    }

}
