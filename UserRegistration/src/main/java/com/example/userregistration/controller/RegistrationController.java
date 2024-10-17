package com.example.userregistration.controller;



import com.example.userregistration.model.User;
import com.example.userregistration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result , Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        if (userService.doesUserExist(user.getUserName())) {
            result.rejectValue("userName", "user.exist", "This username is already taken");
            return "register";
        }

        if (userService.doesEmailExist(user.getEmail())) {
            result.rejectValue("email", "user.email.exist", "This email is already taken");
            return "register";
        }

        userService.registerNewUser(user);
        return "redirect:/registration-successful";
    }

    @GetMapping("/registration-successful")
    public String showRegistrationSuccessful(){
        return "registration-successful";
    }
}
