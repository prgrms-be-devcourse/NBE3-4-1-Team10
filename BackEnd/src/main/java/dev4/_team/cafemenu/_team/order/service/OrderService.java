package dev4._team.cafemenu._team.order.service;

import dev4._team.cafemenu._team.error.BusinessException;
import dev4._team.cafemenu._team.error.ErrorCode;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        Order newOrder = Order.builder()
                .product(order.getProduct())
                .user(order.getUser())
                .address(order.getAddress())
                .post(order.getPost())
                .time(order.getTime())
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
}
