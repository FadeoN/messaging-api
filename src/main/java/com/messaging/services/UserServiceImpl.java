package com.messaging.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.messaging.converters.UserMapper;
import com.messaging.dtos.UserDTO;
import com.messaging.exceptions.*;
import com.messaging.models.user.BlockRelation;
import com.messaging.models.user.Role;
import com.messaging.models.user.User;
import com.messaging.repository.user.BlockRepository;
import com.messaging.repository.user.UserRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BlockRepository blockRepository;

    private UserMapper userMapper;
    private UserPatchUtil userPatchUtil;
    private RoleService roleService;
    private AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(BlockRepository blockRepository, UserRepository userRepository, UserMapper userMapper, UserPatchUtil userPatchUtil, RoleService roleService,
                           @Lazy AuthenticationManager authenticationManager, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userPatchUtil = userPatchUtil;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.blockRepository = blockRepository;
    }

    @Override
    public User create(UserDTO userDTO) {

        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new RestUsernameAlreadyExistsException();
        }

        User user = userMapper.toEntity(userDTO);
        Set<Role> roles = new HashSet<>();
        // TODO: Use Enum or interface instead of string
        Role role = roleService.getOrCreateRole("USER");
        roles.add(role);

        user = user.toBuilder()
                .roles(roles)
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword())).build();

        userRepository.save(user);

        return user;
    }

    @Override
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

    @Override
    public Optional<User> getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() ? user : Optional.empty();

    }


    @Override
    public List<BlockRelation> getAllBlocked(String username) {
        return blockRepository.getAllBlockedUsers(username);
    }

    @Override
    public BlockRelation blockUser(String loggedInUsername, String targetUsername) {

        if(loggedInUsername.equals(targetUsername)) throw new RestMethodArgumentNotValidException();

        Optional<User> targetUser = userRepository.findByUsername(targetUsername);
        if(targetUser.isEmpty()) throw new RestResourceNotFoundException();
        if(blockRepository.findByUsernameAndBlockedUsername(loggedInUsername, targetUsername).isPresent()) throw new RestUserAlreadyBlockedException();

        BlockRelation blockRelation = BlockRelation.builder()
                .user(userRepository.findByUsername(loggedInUsername).get())
                .blockedUser(targetUser.get()).build();

        return blockRepository.save(blockRelation);
    }

    @Override
    public void unblockUser(String loggedInUsername, String blockedUsername) {

        if(loggedInUsername.equals(blockedUsername)) throw new RestMethodArgumentNotValidException();

        Optional<User> targetUser = userRepository.findByUsername(blockedUsername);
        if(targetUser.isEmpty()) throw new RestResourceNotFoundException();

        Optional<BlockRelation> blockRelation = blockRepository.findByUsernameAndBlockedUsername(loggedInUsername, blockedUsername);
        if(blockRelation.isEmpty()) throw new RestUserAlreadyUnblockedException();

        blockRepository.deleteById(blockRelation.get().getId());
    }

    @Override
    public boolean isBlockedByRecipientUser(String loggedInUsername, String targetUsername) {
        return blockRepository.findByUsernameAndBlockedUsername(targetUsername, loggedInUsername).isPresent();
    }


}
