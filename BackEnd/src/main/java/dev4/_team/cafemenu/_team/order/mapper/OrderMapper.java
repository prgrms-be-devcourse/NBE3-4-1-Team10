package dev4._team.cafemenu._team.order.mapper;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductResponseDto;
import dev4._team.cafemenu._team.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Orders toEntity(OrderDto dto, User user) {
        return Orders.builder()
                .user(user)
                .address(dto.getAddress())
                .post(dto.getPost())
                .time(LocalDateTime.now())
                .status(LocalDateTime.now().getHour() >= 14 ? "내일 배송" : "배송 중")
                .build();
    }

    public static OrderResponseDto toDto(Orders orders, List<OrderProductResponseDto> orderProductResponseDtos) {
        return OrderResponseDto.builder()
                .address(orders.getAddress())
                .post(orders.getPost())
                .status(orders.getStatus())
                .orderProduct(orderProductResponseDtos)
                .build();
    }

    public static List<OrderResponseDto> getOrderResponseDtos(List<Orders> ordersList) {
        return ordersList.stream()
                .map(orders -> {
                    List<OrderProductResponseDto> orderProductDtos = orders.getOrderProducts().stream()
                            .map(OrderProductResponseDto::of)
                            .collect(Collectors.toList());
                    return OrderMapper.toDto(orders, orderProductDtos);
                })
                .collect(Collectors.toList());
    }
}
