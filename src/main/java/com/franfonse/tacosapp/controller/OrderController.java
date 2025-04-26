package com.franfonse.tacosapp.controller;

import com.franfonse.tacosapp.model.MenuItem;
import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.OrderItem;
import com.franfonse.tacosapp.model.User;
import com.franfonse.tacosapp.service.MenuItemService;
import com.franfonse.tacosapp.service.OrderService;
import com.franfonse.tacosapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/new")
    public String newOrder(@RequestParam Long userId, Model model) {

        User user = userService.findUserById(userId);

        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "not-found";
        }

        Order order = new Order(user);
        orderService.saveOrder(order);

        model.addAttribute("order", order);
        model.addAttribute("sortedMenuItems", menuItemService.getSortedMenuItems());

        return "menu";
    }

    @PostMapping("/add-item")
    public String addItemToOrder(@RequestParam Long orderId, @RequestParam Long menuItemId, @RequestParam int quantity, Model model) {

        Order order = orderService.findOrderById(orderId);
        MenuItem menuItem = menuItemService.findMenuItemById(menuItemId);

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

        orderService.saveOrder(order);

        model.addAttribute("sortedMenuItems", menuItemService.getSortedMenuItems());
        model.addAttribute("order", order);
        model.addAttribute("totalItems", totalItems);

        return "menu";

    }

    @GetMapping("/add-items")
    public String addMoreItems(@RequestParam Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);
        User user = userService.findUserById(order.getUser().getId());

        model.addAttribute("sortedMenuItems", menuItemService.getSortedMenuItems());
        model.addAttribute("order", order);

        return "menu";
    }

    @GetMapping("/view")
    public String viewOrder(@RequestParam Long orderId, Model model) {

        Order order = orderService.findOrderById(orderId);

        if (order == null) {
            model.addAttribute("errorMessage", "Order not found. ID: " + orderId);
            return "not-found";
        }

        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping("/delete")
    public String deleteOrder(@RequestParam Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);

        if (order == null) {
            model.addAttribute("errorMessage", "Order not found. ID " + orderId);
            return "not-found";
        }

        orderService.deleteOrder(order);
        model.addAttribute("user", order.getUser());

        return "orders";
    }

    @PostMapping("/checkout")
    public String checkoutOrder(@RequestParam Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);

        if (order == null) {
            model.addAttribute("errorMessage", "Order not found. ID: " + orderId);
            return "not-found";
        }

        order = orderService.checkoutOrder(order);

        model.addAttribute("order", order);

        return "invoice";
    }

    @GetMapping("/invoice")
    public String viewInvoice(@RequestParam Long orderId, Model model) {

        Order order = orderService.findOrderById(orderId);

        if (order == null) {
            model.addAttribute("errorMessage", "Order not found. ID: " + orderId);
            return "not-found";
        }

        model.addAttribute("order", order);
        return "invoice";
    }

    @GetMapping("/view-all")
    public String viewOrders(@RequestParam Long userId, Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);

        return "orders";
    }

}
