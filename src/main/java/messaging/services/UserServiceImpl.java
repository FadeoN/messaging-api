package messaging.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import messaging.converters.UserMapper;
import messaging.dtos.UserDTO;
import messaging.exceptions.RestResourceNotFoundException;
import messaging.exceptions.RestUsernameAlreadyExistsException;
import messaging.models.user.User;
import messaging.repository.UserRepository;
import messaging.utils.UserPatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;
    private UserPatchUtil userPatchUtil;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserPatchUtil userPatchUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userPatchUtil = userPatchUtil;
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RestUsernameAlreadyExistsException();
        }
        userRepository.save(user);

        return user;
    }

    @Override
    public User update(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new RestResourceNotFoundException();

        User trackPatched = userPatchUtil.applyPatchToUser(patch, user.get());

        userRepository.save(trackPatched);

        return trackPatched;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDTO> getByID(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> userMapper.toDTO(value));

    }

    @Override
    public Optional<UserDTO> getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> userMapper.toDTO(value));

    }


}
