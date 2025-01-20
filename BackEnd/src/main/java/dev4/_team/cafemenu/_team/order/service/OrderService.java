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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;


    @Transactional
    public void createOrder(OrderDto orderDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        log.info("회원 조회");
        Orders orders = OrderMapper.toEntity(orderDto, user);
        orderRepository.save(orders);
        log.info("주문 저장");
        saveOrderProduct(orderDto, orders);
        log.info("주문상품 저장");
        LocalDateTime now = LocalDateTime.now();

        if (now.getHour() >= 14) {
            orderDto.setStatus("내일 배송");
        } else {
            orderDto.setStatus("오늘 배송");
        }


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

    public void delete(Long orderId, Long userId) {
        List<Orders> orders = orderRepository.findByUserId(userId);
        Orders findOrder = orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND, "해당 주문은 존재하지 않습니다."));

        orderRepository.delete(findOrder);
    }


    private List<Orders> getByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    private Optional<Orders> getById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Orders updateOrder(Long orderId, OrderDto orderDto, Long userId) {

        List<Orders> orders = getByUserId(userId);
        if (orders.isEmpty()) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "해당 사용자는 주문 내역이 없습니다.");
        }

        Orders findOrder = orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND, "해당 주문은 존재하지 않습니다."));

        Orders updatedOrder = OrderMapper.updateEntity(findOrder, orderDto);

        return orderRepository.save(updatedOrder);
    }
}