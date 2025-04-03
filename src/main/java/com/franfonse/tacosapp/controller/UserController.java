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

    @GetMapping("/signUser")
    public String signUser(@RequestParam String username, Model model) {

        // Validate correct format of the username
        if (username.length() > 60 || username.contains(" ") || Character.isDigit(username.charAt(0))) {
            model.addAttribute("errorMessage", "Must start with a letter, contain only letters or numbers, and be less than 60 characters");
            return "user";
        }

        // Lowercase the username
        username = userService.sanitizeUsername(username);

        // Find or create the user
        User user = userService.findOrCreateUsername(username);

        // Add user and orders to the model
        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());

        return "orders";
    }

}
