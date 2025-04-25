package com.franfonse.tacosapp.controller;
import com.franfonse.tacosapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.franfonse.tacosapp.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String signUser() {
        return "user";
    }

    @GetMapping("/sign")
    public String signUser(@RequestParam String username, Model model) {

        if (!userService.validateUsernameFormat(username)) {
            model.addAttribute("errorMessage", "Must start with a letter, contain only letters or numbers, and be less than 60 characters");
            return "user";
        }

        // Lowercase username
        username = userService.sanitizeUsername(username);
        // Find or create the user
        User user = userService.findOrCreateUsername(username);
        // Add user and orders to the model
        model.addAttribute("user", user);

        return "orders";
    }

    @GetMapping("/view-orders")
    public String viewOrders(@RequestParam Long userId, Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);

        return "orders";
    }
}
