package messaging.converters;

import messaging.dtos.UserDTO;
import messaging.models.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper{

    @Override
    public UserDTO toDTO(User entity) {
        return UserDTO.builder()
                .username(entity.getUsername())
                .build();
    }

    @Override
    public User toEntity(UserDTO dto) {

        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
