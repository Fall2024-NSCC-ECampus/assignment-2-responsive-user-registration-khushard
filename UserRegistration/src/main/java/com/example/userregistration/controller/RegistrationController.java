package com.example.userregistration.controller;
import com.example.userregistration.model.User;
import com.example.userregistration.repository.UserRepository;
import com.example.userregistration.service.LoginDTO;
import com.example.userregistration.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
* Controller that handles user registration and authentication.
* Using same controller for both registration and login functions
* */

@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
    * Displays the registration form.
    * Adds an empty user object to the model
    * */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Displays the dashboard for successful logins
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "dashboard";
    }


    /**
    * Processes the registration form submission. Creates new user in database
    * Validates input and checks for duplicate usernames or email.
    * @param user - The user data from the form
    * @param result - Validation results
    * */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {

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

   // Displays the registration successful page
    @GetMapping("/registration-successful")
    public String showRegistrationSuccessful() {
        return "registration-successful";
    }

    /**
    * Displays the login form
    * Uses LoginDTO for data transfer
    * */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    /**
     * Processes logins
     * Validates credentials and manages sessions
     * @param loginDTO Holds the login credentials
     * @param result Validation result
     * @param session HTTP session management
     * @return Redirects to dashboard on successful login, returns to the login form if failed.
     * */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginDTO") @Valid LoginDTO loginDTO,
                            BindingResult result,
                            HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }

        if (!userService.validateUser(loginDTO.getUserName(), loginDTO.getPassword())) {
            result.rejectValue("userName", "invalid.credentials", "Invalid username or password");
            return "login";
        }

        User authenticatedUser = userService.getUserByUsername(loginDTO.getUserName());
        session.setAttribute("user", authenticatedUser);
        return "redirect:/dashboard";
    }

    /**
     * Invalidates the session and redirect to log in screen.
     * */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";


    }
}
