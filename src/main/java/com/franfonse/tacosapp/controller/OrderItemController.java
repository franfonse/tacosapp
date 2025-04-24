package com.franfonse.tacosapp.controller;
import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.OrderItem;
import com.franfonse.tacosapp.respository.OrderItemRepository;
import com.franfonse.tacosapp.respository.OrderRepository;
import com.franfonse.tacosapp.service.OrderItemService;
import com.franfonse.tacosapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService, OrderService orderService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }

    @DeleteMapping("/delete")
    public String deleteOrderItem(@RequestParam Long orderItemId, Model model) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);

        if (orderItem == null) {
            model.addAttribute("errorMessage", "Order item not found");
            return "not-found";
        }

        Order order = orderRepository.findById(orderItem.getOrder().getId()).orElse(null);

        if (order == null) {
            model.addAttribute("errorMessage", "Order not found");
            return "not-found";
        }
        orderItemRepository.delete(orderItem);

        List<OrderItem> orderItems = order.getOrderItems();

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("orderItemDeletedMessage", "Order item deleted successfully");

        return "order";
    }

    @PatchMapping("/editQuantity")
    public String editQuantity(@RequestParam Long orderItemId, @RequestParam int quantity, Model model) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);

        if (orderItem == null) {
            model.addAttribute("errorMessage", "Order item not found");
            return "not-found";
        }

        orderItem.setQuantity(quantity);
        orderItemRepository.save(orderItem);
        model.addAttribute("orderItemUpdatedMessage", "Order item quantity updated successfully");

        return "order";
    }

}
