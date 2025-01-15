package dev4._team.cafemenu._team.order.service;


import dev4._team.cafemenu._team.error.BusinessException;
import dev4._team.cafemenu._team.error.ErrorCode;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;

    public void delete(Order order) {
        Optional<Order> deleteorder = (orderRepository.findOrderById(order.getId()));
        if (deleteorder.isEmpty()) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "order not found");
        } else {
            orderRepository.delete(deleteorder.get());
        }
    }
}
