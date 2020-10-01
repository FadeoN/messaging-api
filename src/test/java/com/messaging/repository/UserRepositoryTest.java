package com.messaging.repository;

import com.messaging.models.user.User;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


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
