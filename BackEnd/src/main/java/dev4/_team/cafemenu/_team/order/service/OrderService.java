package dev4._team.cafemenu._team.order.service;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.mapper.OrderMapper;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Orders createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Orders orders = OrderMapper.toEntity(orderDto, user);
        return orderRepository.save(orders);
    }


    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Orders> orders = orderRepository.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }

        return OrderMapper.toDtoList(orders);
    }

    public void delete(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        orderRepository.delete(orders);
    }
}