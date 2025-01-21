package dev4._team.cafemenu._team.order.controller;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.mapper.OrderMapper;
import dev4._team.cafemenu._team.order.service.OrderService;
import dev4._team.cafemenu._team.security.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDto orderDto,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        orderService.createOrder(orderDto, customUserDetails.getUser().getId());
        return ResponseEntity.status(201).body(null);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable(name = "orderId") Long orderId,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        orderService.delete(orderId, customUserDetails.getUser().getId());
        return ResponseEntity.ok("삭제에 성공했습니다!");
    }

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(customUserDetails.getUser().getId());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(
            @PathVariable(name = "orderId") Long orderId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid OrderDto orderDto) {
        orderService.updateOrder(orderId, orderDto, customUserDetails.getUser().getId());
        return ResponseEntity.status(201).body(null);
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
