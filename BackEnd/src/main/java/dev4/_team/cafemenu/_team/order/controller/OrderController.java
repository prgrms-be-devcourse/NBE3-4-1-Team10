package dev4._team.cafemenu._team.order.controller;

import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private OrderService orderService;

    @DeleteMapping
    public void deleteOrder(@RequestBody Order order) {
        orderService.delete(order);
    }

}
