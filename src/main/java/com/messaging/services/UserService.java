package com.messaging.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.messaging.dtos.UserDTO;
import com.messaging.models.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService, UserRelationService {

    /* CRUD */
    User create(UserDTO user);
    User update(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;
    void delete(Long id);

    /* Find */
    Optional<UserDTO> getByID(Long id);
    Optional<User> getByUsername(String username);

    /* Auth ops */
    void autoLogin(String username, String password);



}
