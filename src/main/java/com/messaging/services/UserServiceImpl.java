package com.messaging.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.messaging.converters.UserMapper;
import com.messaging.dtos.UserDTO;
import com.messaging.exceptions.RestResourceNotFoundException;
import com.messaging.exceptions.RestUsernameAlreadyExistsException;
import com.messaging.models.user.Role;
import com.messaging.models.user.User;
import com.messaging.repository.UserRepository;
import com.messaging.utils.UserPatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;
    private UserPatchUtil userPatchUtil;
    private RoleService roleService;
    private AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserPatchUtil userPatchUtil, RoleService roleService,
                           @Lazy AuthenticationManager authenticationManager, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userPatchUtil = userPatchUtil;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(UserDTO userDTO) {

        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new RestUsernameAlreadyExistsException();
        }

        User user = userMapper.toEntity(userDTO);
        Set<Role> roles = new HashSet<>();
        Role role = roleService.getOrCreateRole("USER");
        roles.add(role);

        user = user.toBuilder()
                .roles(roles)
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword())).build();

        userRepository.save(user);

        return user;
    }

    public void autoLogin(String username, String password) {
        UserDetails userDetails = this.getByUsername(username).get();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password,
                        userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    @Override
    public User getLoggedInUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User) {
            return (User) user;
        }
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.getByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(
                    String.format("User with email %s was not found!", username));
        }
        return user.get();
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


    public Optional<User> getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() ? user : Optional.empty();

    }


}
