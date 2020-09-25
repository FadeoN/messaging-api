package messaging.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import messaging.dtos.UserDTO;
import messaging.models.user.User;

import java.util.Optional;

public interface UserService {

    User create(UserDTO user);

    User update(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

    void delete(Long id);

    Optional<User> getByID(Long id);

}
