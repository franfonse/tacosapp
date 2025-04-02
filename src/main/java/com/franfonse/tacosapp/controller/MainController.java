package com.franfonse.tacosapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/signUser")
    public String signUser() {
        return "user";
    }

    @GetMapping("/orders")
    public String showOrders(@RequestParam Long userId, Model model) {
        return "orders";
    }
}
