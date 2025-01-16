package dev4._team.cafemenu._team.order.service;


import dev4._team.cafemenu._team.error.BusinessException;
import dev4._team.cafemenu._team.error.ErrorCode;
import dev4._team.cafemenu._team.order.entity.Order;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import dev4._team.cafemenu._team.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final UserRepository userRepository;

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

    public User getOrder(Long id) {
        Optional<User> userOrder = userRepository.findOrderListById(id);
        if (userOrder.isEmpty()) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "order not found");
        } else {
            return userOrder.get();
        }
    }

//    public void delete(Order order) {
//        Optional<Order> deleteorder = (orderRepository.finduserBy(order.getId()));
//        if (deleteorder.isEmpty()) {
//            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "order not found");
//        } else {
//            orderRepository.delete(deleteorder.get());
//        }
//    }
}