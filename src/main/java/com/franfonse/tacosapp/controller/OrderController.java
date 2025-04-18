package com.franfonse.tacosapp.controller;

import com.franfonse.tacosapp.model.MenuItem;
import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.OrderItem;
import com.franfonse.tacosapp.model.User;
import com.franfonse.tacosapp.respository.MenuItemRepository;
import com.franfonse.tacosapp.respository.OrderRepository;
import com.franfonse.tacosapp.service.MenuItemService;
import com.franfonse.tacosapp.service.OrderService;
import com.franfonse.tacosapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final MenuItemService menuItemService;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, MenuItemService menuItemService, OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderService = orderService;
        this.userService = userService;
        this.menuItemService = menuItemService;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping("/newOrder")
    public String newOrder(@RequestParam String username, Model model) {

        User user = userService.findByUsername(username).orElse(null);

        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "not-found";
        }

        Order order = new Order(user);
        orderRepository.save(order);

        model.addAttribute("order", order);
        model.addAttribute("sortedMenuItems", menuItemService.getSortedMenuItems());
        model.addAttribute("totalItems", 0);

        return "menu";
    }

    @PostMapping("/addItemToOrder")
    public String addItemToOrder(@RequestParam Long orderId, @RequestParam Long menuItemId, @RequestParam int quantity, Model model) {

        Order order = orderRepository.findById(orderId).orElse(null);
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);

        if (order == null || menuItem == null) {
            model.addAttribute("errorMessage", "Order or Menu Item not found");
            return "not-found";
        }

        // Check if item already exists in order
        OrderItem existingItem = order.getOrderItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Update quantity if item already exists
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setTotalPrice(existingItem.getMenuItem().getPrice() * existingItem.getQuantity());
        } else {
            // Create new OrderItem if it doesn't exist
            OrderItem newItem = new OrderItem(order, menuItem, quantity);
            order.getOrderItems().add(newItem);
        }

        // Update order total
        double newTotal = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        order.setTotalCost(newTotal);

        // Calculate total items in order
        int totalItems = order.getOrderItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        orderRepository.save(order);

        model.addAttribute("sortedMenuItems", menuItemService.getSortedMenuItems());
        model.addAttribute("order", order);
        model.addAttribute("totalItems", totalItems);

        return "menu";

    }

}
