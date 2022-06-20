package com.example.orderservice.controllers;

import com.example.orderservice.services.OrderService;
import com.example.orderservice.services.UserService;
import contract.entities.Order;
import contract.entities.ShoppingCart;
import contract.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderControllerImpl implements OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Order makeOrder(ShoppingCart cart, String user) {
        return orderService.makeOrder(cart, userService.findByUserName(user));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Override
    public Order findById(Long id) {
        return orderService.findById(id);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderService.saveOrder(order);
    }

    @Override
    public Order changeOrderStatus(Order order, Long statusId) {
        return orderService.changeOrderStatus(order, statusId);
    }
}
