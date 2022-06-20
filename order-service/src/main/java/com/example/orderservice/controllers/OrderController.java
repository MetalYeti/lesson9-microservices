package com.example.orderservice.controllers;

import contract.entities.Order;
import contract.entities.ShoppingCart;
import contract.entities.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface OrderController {
    @PostMapping("/order/add")
    Order makeOrder(@RequestBody ShoppingCart cart, @RequestParam("username") String user);

    @GetMapping("/orders")
    List<Order> getAllOrders();

    @GetMapping("/order/get/{id}")
    Order findById(@PathVariable("id") Long id);

    @PostMapping("/order/save")
    Order saveOrder(@RequestBody Order order);

    @PutMapping("/order/change/{id}")
    Order changeOrderStatus(@RequestBody Order order, @PathVariable("id") Long statusId);
}
