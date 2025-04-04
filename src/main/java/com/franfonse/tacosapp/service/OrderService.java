package com.franfonse.tacosapp.service;

import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.User;
import com.franfonse.tacosapp.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public void addOrderToUser(String username, Order order) {
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            user.addOrder(order);
            orderRepository.save(order);
        }
    }
}
