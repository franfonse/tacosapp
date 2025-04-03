package com.franfonse.tacosapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value="/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String signUser() {
        return "user";
    }

}
