package com.messaging.services;

import com.messaging.models.user.Role;

public interface RoleService {

    Role getOrCreateRole(String name);

}
