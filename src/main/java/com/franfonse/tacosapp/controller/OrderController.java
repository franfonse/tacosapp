package com.franfonse.tacosapp.controller;

import com.franfonse.tacosapp.model.MenuItem;
import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.User;
import com.franfonse.tacosapp.service.MenuItemService;
import com.franfonse.tacosapp.service.OrderService;
import com.franfonse.tacosapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final MenuItemService menuItemService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, MenuItemService menuItemService) {
        this.orderService = orderService;
        this.userService = userService;
        this.menuItemService = menuItemService;
    }

    @GetMapping("/newOrder")
    public String newOrder(Model model) {

//        User user = userService.findByUsername(username).orElse(null);
//
//        if (user == null) {
//            model.addAttribute("errorMessage", "User not found");
//            return "not-found";
//        }

        Order order = new Order();
        model.addAttribute("order", order);

        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        model.addAttribute("menuItems", menuItems);

        return "menu";
    }

}
