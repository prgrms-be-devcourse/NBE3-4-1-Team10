package dev4._team.cafemenu._team.order.controller;

import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);

    @DeleteMapping
    public void deleteOrder(@RequestBody Order order) {
        orderService.delete(order);
    }

}
