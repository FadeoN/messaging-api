package messaging.converters;

import messaging.dtos.UserDTO;
import messaging.models.user.User;

public interface UserMapper {


    UserDTO toDTO(User entity);

    User toEntity(UserDTO dto);
}
