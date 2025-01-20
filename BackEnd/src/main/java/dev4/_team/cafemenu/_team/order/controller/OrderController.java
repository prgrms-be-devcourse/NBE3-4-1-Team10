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
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId, Long userId) {
        orderService.delete(orderId, userId);
        return ResponseEntity.ok("삭제에 성공했습니다!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable Long orderId, Long userId,
            @RequestBody @Valid OrderDto orderDto) {
        Orders updatedOrder = orderService.updateOrder(orderId, orderDto, userId);
        return ResponseEntity.ok(OrderMapper.toDto(updatedOrder));
    }

    @GetMapping("/today-delivery")
    public ResponseEntity<List<OrderResponseDto>> getTodayDeliveryOrders() {
        List<OrderResponseDto> todayDeliveryOrders = orderService.getOrdersByStatus("오늘 배송");
        return ResponseEntity.ok(todayDeliveryOrders);
    }

    @GetMapping("/tomorrow-delivery")
    public ResponseEntity<List<OrderResponseDto>> getTomorrowDeliveryOrders() {
        List<OrderResponseDto> tomorrowDeliveryOrders = orderService.getOrdersByStatus("내일 배송");
        return ResponseEntity.ok(tomorrowDeliveryOrders);
    }
}
