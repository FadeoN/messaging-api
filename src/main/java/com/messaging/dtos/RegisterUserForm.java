package com.messaging.dtos;


import com.messaging.validators.PasswordConstraintValidator.ValidPassword;
import com.messaging.validators.PasswordMatchesValidator.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserForm {

    @NotEmpty
    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotEmpty
    @NotNull
    @ValidPassword
    private String password;

    @NotBlank
    @NotEmpty
    @NotNull
    private String passwordConfirm;

}