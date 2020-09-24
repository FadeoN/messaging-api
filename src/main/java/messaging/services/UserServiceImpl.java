package messaging.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import messaging.converters.UserMapper;
import messaging.dtos.UserDTO;
import messaging.exceptions.RestResourceNotFoundException;
import messaging.models.user.User;
import messaging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;
    private ObjectMapper objectMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new RestResourceNotFoundException();

        User trackPatched = applyPatchToCustomer(patch, user.get());

        userRepository.save(trackPatched);

        return trackPatched;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getByID(Long id) {
        return userRepository.findById(id);
    }

    private User applyPatchToCustomer(JsonPatch patch, User targetCustomer) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }
}
