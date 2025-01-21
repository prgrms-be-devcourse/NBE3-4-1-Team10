package dev4._team.cafemenu._team.order.service;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.mapper.OrderMapper;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductDto;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductResponseDto;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.orderProduct.repository.OrderProductRepository;
import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.product.repository.ProductRepository;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;


    public void createOrder(OrderDto orderDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Orders orders = OrderMapper.toEntity(orderDto, user);
        orderRepository.save(orders);
        saveOrderProduct(orderDto, orders);
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
        List<Orders> ordersList = getByUserId(userId); // 유저 ID로 주문 조회
        return OrderMapper.getOrderResponseDtos(ordersList);
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

    @Transactional
    public void updateOrder(Long orderId, OrderDto orderDto, Long userId) {

        List<Orders> orders = getByUserId(userId);
        if (orders.isEmpty()) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "해당 사용자는 주문 내역이 없습니다.");
        }

        Orders findOrder = orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND, "해당 주문은 존재하지 않습니다."));
        findOrder.updateOrder(orderDto.getAddress(), orderDto.getPost());
        // OrderProduct 업데이트
        updateOrderProducts(findOrder, orderDto.getOrderProductDto());
    }

    private void updateOrderProducts(Orders existingOrder, List<OrderProductDto> orderProductDtos) {
        List<OrderProduct> existingOrderProducts = existingOrder.getOrderProducts();

        for (OrderProductDto orderProductDto : orderProductDtos) {
            Long productId = orderProductDto.getProductId();

            OrderProduct existingOrderProduct = existingOrderProducts.stream()
                    .filter(op -> op.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT));
            existingOrderProduct.updateCountAndPrice(orderProductDto.getCount());
        }
    }

    public List<OrderResponseDto> getOrdersByStatus(String status) {
        List<Orders> orders = orderRepository.findByStatus(status);
        return OrderMapper.getOrderResponseDtos(orders);
    }
}