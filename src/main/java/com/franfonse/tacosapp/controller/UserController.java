package com.franfonse.tacosapp.controller;
import com.franfonse.tacosapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.franfonse.tacosapp.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, Model model) {

        if (!userService.validateUsernameFormat(username)) {
            model.addAttribute("errorMessage", "Must start with a letter, contain only letters or numbers, and be less than 60 characters");
            return "login";
        }

        // Lowercase username
        username = userService.sanitizeUsername(username);
        // Find or create the user
        User user = userService.findByUsername(username).orElse(null);

        if (user == null) {
            model.addAttribute("errorMessage", "User not found. Username: " + username);
            return "login";
        }

        // Add user and orders to the model
        model.addAttribute("user", user);

        return "orders";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String password, Model model) {

        if (!userService.validateUsernameFormat(username)) {
            model.addAttribute("errorMessage",
                    "Invalid format: must start with a letter, no spaces, ≤60 chars");
            return "signup";
        }

        String clean = userService.sanitizeUsername(username);
        String hash  = passwordEncoder.encode(password);
        userService.createUser(clean, hash);


        return "redirect:/user/login?registered";
    }

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }

}
