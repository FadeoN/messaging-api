package messaging.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder
public class UserDTO {

    @NotNull(message = "username can not be empty.")
    private String username;

    @NotNull(message = "password can not be empty.")
    private String password;

}
