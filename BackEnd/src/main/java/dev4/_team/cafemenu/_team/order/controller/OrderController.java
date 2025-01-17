package dev4._team.cafemenu._team.order.controller;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.service.OrderService;
import jakarta.validation.Valid;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderDto orderDto) {
//        Order createdOrder = orderService.createOrder(orderDto);
//        return ResponseEntity.ok(createdOrder);
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok("삭제에 성공했습니다!");
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

}
