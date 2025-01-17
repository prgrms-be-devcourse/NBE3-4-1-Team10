package dev4._team.cafemenu._team.order.service;


import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductDto;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import dev4._team.cafemenu._team.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

//    public Order createOrder(OrderDto orderDto) {
//        Order newOrder = Order.builder()
//                .product(orderDto.getProduct())
//                .user(orderDto.getUser())
//                .address(orderDto.getAddress())
//                .post(orderDto.getPost())
//                .time(LocalDateTime.now())
//                .status("주문 대기")
//                .build();
//
//
//        orderRepository.save(newOrder);
//        return newOrder;
//    }

    public List<OrderDto> getOrdersByUserId(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);  //유저의 주문 목록 조회

        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(order.getId());
            orderDto.setUserId(order.getUser().getId());
            orderDto.setAddress(order.getAddress());
            orderDto.setPost(order.getPost());
            orderDto.setTime(order.getTime());

            List<OrderProductDto> orderProductDtos = new ArrayList<>();
            for (OrderProduct orderProduct : order.getOrderProduct()) {
                OrderProductDto orderProductDto = new OrderProductDto();
                orderProductDto.setProductId(orderProduct.getId());
                orderProductDto.setCount(orderProduct.getCount());
                orderProductDto.setPrice(orderProduct.getPrice());
                orderProductDtos.add(orderProductDto);
            }

            orderDto.setOrderProducts(orderProductDtos);

            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public void delete(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        orderRepository.delete(order);
    }
}