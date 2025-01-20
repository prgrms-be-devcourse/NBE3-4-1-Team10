package dev4._team.cafemenu._team.order.service;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.mapper.OrderMapper;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductDto;
import dev4._team.cafemenu._team.orderProduct.repository.OrderProductRepository;
import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.product.repository.ProductRepository;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;


    public Orders createOrder(OrderDto orderDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Orders orders = OrderMapper.toEntity(orderDto, user);
        saveOrderProduct(orderDto, orders);
        LocalDateTime now = LocalDateTime.now();


        if (now.getHour() >= 14) {
            orderDto.setStatus("내일 배송");
        } else {
            orderDto.setStatus("오늘 배송");
        }

        return orderRepository.save(orders);
    }

    private void saveOrderProduct(OrderDto orderDto, Orders orders) {
        orderDto.getOrderProductDto().stream()
                .map(orderProductDto -> {
                    Product product = productRepository.findById(orderProductDto.getProductId())
                            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT));
                    return orderProductDto.toOrderProductEntity(orders, product);
                })
                .forEach(orderProductRepository::save);
    }

    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        List<Orders> orders = getByUserId(userId);

        if (orders.isEmpty()) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }

        return OrderMapper.toDtoList(orders);
    }

    public void delete(Long orderId) {
        Orders orders = getById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        orderRepository.delete(orders);
    }


    private List<Orders> getByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    private Optional<Orders> getById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Orders updateOrder(Long orderId, OrderDto orderDto) {
        Orders existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        Orders updatedOrder = OrderMapper.updateEntity(existingOrder, orderDto);

        return orderRepository.save(updatedOrder);
    }
}