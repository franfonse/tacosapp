package com.franfonse.tacosapp.controller;
import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.OrderItem;
import com.franfonse.tacosapp.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order-item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/delete")
    public String deleteOrderItem(@RequestParam Long orderItemId, Model model) {
        OrderItem orderItem = orderItemService.findOrderItemById(orderItemId);

        if (orderItem == null) {
            model.addAttribute("errorMessage", "Order item not found. ID :" + orderItemId);
            return "not-found";
        }

        Order order = orderItemService.deleteOrderItem(orderItem);

        model.addAttribute("order", order);
        model.addAttribute("orderItemDeletedMessage", "Order item deleted successfully");

        return "order";
    }

    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam Long orderItemId, @RequestParam int quantity, Model model) {

        OrderItem orderItem = orderItemService.findOrderItemById(orderItemId);

        if (orderItem == null) {
            model.addAttribute("errorMessage", "Order item not found. ID: " + orderItemId);
            return "not-found";
        }

        Order order = orderItemService.updateMenuItemQuantity(orderItem, quantity);

        model.addAttribute("order", order);
        model.addAttribute("orderItemUpdatedMessage", "Order item quantity updated successfully");

        return "order";
    }

}
