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

    public UserDTO(@NotNull(message = "username can not be empty.") String username, @NotNull(message = "password can not be empty.") String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(){

    }
}
