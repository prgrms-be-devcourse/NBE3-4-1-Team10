package dev4._team.cafemenu._team.order.controller;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderDto orderDto) {
        Order createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }
    @DeleteMapping
    public void deleteOrder(@RequestBody Order order) {
        orderService.delete(order);
    }

}
