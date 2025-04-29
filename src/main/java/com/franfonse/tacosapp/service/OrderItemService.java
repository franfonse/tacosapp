package com.franfonse.tacosapp.service;

import com.franfonse.tacosapp.model.Order;
import com.franfonse.tacosapp.model.OrderItem;
import com.franfonse.tacosapp.repository.OrderItemRepository;
import com.franfonse.tacosapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public OrderItem findOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElse(null);
    }

    public Order deleteOrderItem(OrderItem orderItem) {
        Long orderId = orderItem.getOrder().getId();
        orderItemRepository.delete(orderItem);
        Order order = orderRepository.findById(orderId).orElse(null);

        double newTotal = 0;

        for (OrderItem item : order.getOrderItems()) {
            newTotal += item.getTotalPrice();
        }

        order.setTotalCost(newTotal);
        orderRepository.save(order);

        return order;
    }

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public Order updateMenuItemQuantity(OrderItem orderItem, int quantity) {

        Order order = orderRepository.findById(orderItem.getOrder().getId()).orElse(null);

        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(orderItem.getMenuItem().getPrice() * quantity);
        double newTotal = 0;

        for (OrderItem item : order.getOrderItems()) {
            newTotal += item.getTotalPrice();
        }

        order.setTotalCost(newTotal);

        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        return order;
    }

}
