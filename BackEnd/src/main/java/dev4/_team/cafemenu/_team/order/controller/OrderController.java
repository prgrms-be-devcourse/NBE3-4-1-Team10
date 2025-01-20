package dev4._team.cafemenu._team.order.controller;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.mapper.OrderMapper;
import dev4._team.cafemenu._team.order.service.OrderService;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
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
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderDto orderDto, Long userId) {
        Orders createdOrder = orderService.createOrder(orderDto, userId);
        return ResponseEntity.status(201).body(OrderMapper.toDto(createdOrder));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok("삭제에 성공했습니다!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderDto orderDto) {
        Orders updatedOrder = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(OrderMapper.toDto(updatedOrder));
    }

}
