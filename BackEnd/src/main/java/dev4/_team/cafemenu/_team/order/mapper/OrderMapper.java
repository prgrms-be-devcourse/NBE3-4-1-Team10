package dev4._team.cafemenu._team.order.mapper;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class OrderMapper {

    public static Orders toEntity(OrderDto dto, User user) {
        return Orders.builder()
                .user(user)
                .address(dto.getAddress())
                .post(dto.getPost())
                .time(LocalDateTime.now())
                .status(dto.getStatus())
                .build();
    }

    public static OrderResponseDto toDto(Orders orders) {
        return OrderResponseDto.builder()
                .userId(orders.getUser().getId())
                .address(orders.getAddress())
                .post(orders.getPost())
                .build();
    }

    public static List<OrderResponseDto> toDtoList(List<Orders> ordersList) {
        return ordersList.stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public static Orders updateEntity(Orders existingOrder, OrderDto dto) {
        // 기존 값은 그대로 유지하고, 필요한 필드만 수정
        return Orders.builder()
                .id(existingOrder.getId())
                .user(existingOrder.getUser())  // 기존 사용자 정보 유지
                .product(existingOrder.getProduct())  // 기존 제품 정보 유지
                .orderProduct(existingOrder.getOrderProduct())  // 기존 상품 정보 유지
                .address(dto.getAddress())  // 수정된 값
                .post(dto.getPost())  // 수정된 값
                .status(dto.getStatus())  // 수정된 값
                .time(existingOrder.getTime())  // 기존 시간 유지
                .build();
    }
}
