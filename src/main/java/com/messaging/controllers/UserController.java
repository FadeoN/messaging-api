package com.messaging.controllers;

import com.messaging.dtos.UserDTO;
import com.messaging.services.UserService;
import com.messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
//@RequestMapping(URLConstants.USERS_BASE_URL)
@Validated
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    ModelAndView getRegister(Model model) {
        model.addAttribute("registerUserForm", new RegisterUserForm());
        return new ModelAndView("register", "registerUserForm", new RegisterUserForm());
    }

    @PostMapping("/register")
    ModelAndView postRegister(@ModelAttribute("registerUserForm") @Valid RegisterUserForm userForm,
                              BindingResult bindingResult) {
        // REMOVE This part catch username error

        if (userService.getByUsername(userForm.getUsername()).isPresent()) {
            bindingResult
                    .addError(new FieldError(userForm.toString(), "username", "Username is already taken!"));
        }
        System.out.println(bindingResult.toString());

        if (!bindingResult.hasErrors()) {

            UserDTO user = new UserDTO();
            user.setUsername(userForm.getUsername().toLowerCase());
            user.setPassword(userForm.getPassword());
            userService.create(user);
            userService.autoLogin(userForm.getUsername(), userForm.getPassword());
            return new ModelAndView("redirect:/home");
        } else {
            userForm.setUsername(userForm.getUsername().toLowerCase());
            ModelAndView template = new ModelAndView("register", "registerUserForm", userForm);
            template.setStatus(HttpStatus.BAD_REQUEST);
            return template;
        }
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("warning", "Invalid credentials");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response,
                             RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        redirectAttributes.addFlashAttribute("success", "You have been logged out");
        return "redirect:/login";
    }

}