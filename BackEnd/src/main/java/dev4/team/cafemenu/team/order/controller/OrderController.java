package dev4.team.cafemenu.team.order.controller;

import dev4.team.cafemenu.team.order.dto.OrderDto;
import dev4.team.cafemenu.team.order.entity.Orders;
import dev4.team.cafemenu.team.order.mapper.OrderMapper;
import dev4.team.cafemenu.team.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        Orders createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(201).body(OrderMapper.toDto(createdOrder));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok("삭제에 성공했습니다!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}
