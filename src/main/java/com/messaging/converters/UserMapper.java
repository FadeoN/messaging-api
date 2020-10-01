package com.messaging.converters;

import com.messaging.dtos.UserDTO;
import com.messaging.models.user.User;

public interface UserMapper {


    UserDTO toDTO(User entity);

    User toEntity(UserDTO dto);
}
