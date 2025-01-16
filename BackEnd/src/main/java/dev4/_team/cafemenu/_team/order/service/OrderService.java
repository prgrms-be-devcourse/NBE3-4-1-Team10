package dev4._team.cafemenu._team.order.service;


import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(OrderDto orderDto) {
        Order newOrder = Order.builder()
                .user(orderDto.getUser())
                .address(orderDto.getAddress())
                .post(orderDto.getPost())
                .time(LocalDateTime.now())
                .status("주문 대기")
                .build();


        orderRepository.save(newOrder);
        return newOrder;
    }

    public Order getOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "order not found");
        } else {
            return order.get();
        }
    }

    public void delete(Order order) {
        Optional<Order> deleteorder = (orderRepository.findOrderById(order.getId()));
        if (deleteorder.isEmpty()) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "order not found");
        } else {
            orderRepository.delete(deleteorder.get());
        }
    }
}