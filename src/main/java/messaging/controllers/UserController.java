package messaging.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import messaging.dtos.UserDTO;
import messaging.models.user.User;
import messaging.services.UserService;
import messaging.utils.URLConstants;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(URLConstants.USERS_BASE_URL)
@Validated
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addItem(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        URI location = URI.create(String.format("%s/%d", URLConstants.USERS_BASE_URL, user.getId()));
        return ResponseEntity.created(location).build();
    }

    @PatchMapping(path = URLConstants.USER_ID, consumes = "application/json-patch+json")
    public ResponseEntity<User> updateItem(@PathVariable("id") Long userID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {

        User user = userService.update(userID, patch);
        return ResponseEntity.ok(user);

    }

    @DeleteMapping (URLConstants.USER_ID)
    public ResponseEntity<Void> deleteItem(@PathVariable ("id") Long userID) {
        userService.delete(userID);
        return ResponseEntity.noContent().build();
    }


    @GetMapping (URLConstants.USER_ID)
    public ResponseEntity<Optional<User>> getItem(@PathVariable ("id") Long userID) {
        Optional<User> user = userService.getByID(userID);
        return ResponseEntity.ok().body(user);
    }

}