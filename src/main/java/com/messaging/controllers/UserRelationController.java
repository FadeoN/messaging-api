package com.messaging.controllers;

import com.messaging.models.user.BlockRelation;
import com.messaging.services.UserService;
import com.messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@Controller
@RestController
@RequestMapping(URLConstants.USERS_BLOCKED_BASE_URL)
@Validated
public class UserRelationController {

    @Autowired
    private UserService userService;


    @PostMapping(URLConstants.USERS_ID_URL)
    public ResponseEntity<Void> blockUser(@AuthenticationPrincipal Principal user,
                                                 @PathVariable("id") String targetUsername) {

        /*Get Current User*/
        String loggedInUsername = user.getName();
        BlockRelation blockRelation = userService.blockUser(loggedInUsername, targetUsername);
        URI location = URI.create(String.format("%s/%d", URLConstants.USERS_BLOCKED_BASE_URL, blockRelation.getId()));

        return ResponseEntity
                .created(location).build();
    }



    @GetMapping
    public ResponseEntity<List<BlockRelation>> blockUser(@AuthenticationPrincipal Principal user) {


        return ResponseEntity
                .ok(userService.getAllBlocked(user.getName()));
    }


    @DeleteMapping(URLConstants.USERS_ID_URL)
    public ResponseEntity<Void> unblockUser(@AuthenticationPrincipal Principal user,
                                                 @PathVariable("id") String targetUsername) {

        /*Get Current User*/
        String loggedInUsername = user.getName();

        userService.unblockUser(loggedInUsername, targetUsername);
        return ResponseEntity
                .noContent().build();
    }



}
