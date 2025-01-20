package dev4.team.cafemenu.team.order.mapper;

import dev4.team.cafemenu.team.order.dto.OrderDto;
import dev4.team.cafemenu.team.order.entity.Orders;
import dev4.team.cafemenu.team.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class OrderMapper {

    public static Orders toEntity(OrderDto dto, User user) {
        return Orders.builder()
                .user(user)
                .address(dto.getAddress())
                .post(dto.getPost())
                .time(LocalDateTime.now())
                .status("주문 대기")
                .build();
    }

    public static OrderDto toDto(Orders orders) {
        return OrderDto.builder()
                .orderId(orders.getId())
                .userId(orders.getUser().getId())
                .address(orders.getAddress())
                .post(orders.getPost())
                .build();
    }

    public static List<OrderDto> toDtoList(List<Orders> ordersList) {
        return ordersList.stream()
                .map(OrderMapper::toDto)
                .toList();
    }
}
