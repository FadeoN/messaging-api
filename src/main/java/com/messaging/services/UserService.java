package com.messaging.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.messaging.dtos.UserDTO;
import com.messaging.models.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User create(UserDTO user);

    User update(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

    void delete(Long id);

    Optional<UserDTO> getByID(Long id);
    Optional<User> getByUsername(String username);


//    User createBasicUser(UserDTO user);

    User getLoggedInUser();

    boolean isAuthenticated();

    void autoLogin(String username, String password);

}
