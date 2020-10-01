package com.messaging.controllers;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
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
    private String password;

    @NotBlank
    @NotEmpty
    @NotNull
    private String passwordConfirm;

}