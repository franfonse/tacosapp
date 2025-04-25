package com.franfonse.tacosapp.service;

import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.User;
import com.franfonse.tacosapp.respository.OrderItemRepository;
import com.franfonse.tacosapp.respository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public void addOrderToUserList(String username, Order order) {
        User user = userService.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User not found. USERNAME: " + username));

        order.setUser(user);
        orderRepository.save(order);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}
