package com.franfonse.tacosapp.controller;
import com.franfonse.tacosapp.model.Order;
import org.springframework.ui.Model;
import com.franfonse.tacosapp.model.MenuItem;
import com.franfonse.tacosapp.respository.MenuItemRepository;
import com.franfonse.tacosapp.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MenuItemController {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemController(OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @DeleteMapping("/deleteOrderItem")
    public String deleteOrderItem(@RequestParam Long menuItemId, Long orderId, Model model) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            model.addAttribute("error", "Item not found");
            return "not-found";
        }
        orderRepository.deleteById(menuItemId);
        Order order = orderRepository.findById(orderId).orElse(null);

        model.addAttribute("order", order);
        return "order";
    }
}
