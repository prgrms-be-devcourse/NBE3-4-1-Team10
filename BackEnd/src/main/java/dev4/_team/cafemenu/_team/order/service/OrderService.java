package dev4._team.cafemenu._team.order.service;


import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import dev4._team.cafemenu._team.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public Order createOrder(OrderDto orderDto) {
        Order newOrder = Order.builder()
                .product(orderDto.getProduct())
                .user(orderDto.getUser())
                .address(orderDto.getAddress())
                .post(orderDto.getPost())
                .time(LocalDateTime.now())
                .status("주문 대기")
                .build();


        orderRepository.save(newOrder);
        return newOrder;
    }

    public User getOrder(Long id) {
        Optional<User> userOrder = userRepository.findOrderListById(id);
        if (userOrder.isEmpty()) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "order not found");
        } else {
            return userOrder.get();
        }
    }

    public void delete(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        orderRepository.delete(order);
    }
}