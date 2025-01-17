package dev4._team.cafemenu._team.order.mapper;

import dev4._team.cafemenu._team.order.dto.OrderDto;
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

    public static OrderDto toDto(Orders orders) {
        return OrderDto.builder()
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
