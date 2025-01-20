package dev4._team.cafemenu._team.order.mapper;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductDto;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Orders toEntity(OrderResponseDto dto, User user) {
        return Orders.builder()
                .user(user)
                .address(dto.getAddress())
                .post(dto.getPost())
                .time(LocalDateTime.now())
                .status(dto.getStatus())
                .orderProduct(dto.getOrderProduct())
                .build();
    }

    public static Orders toEntity(OrderDto dto, User user) {
        return Orders.builder()
                .user(user)
                .address(dto.getAddress())
                .post(dto.getPost())
                .time(LocalDateTime.now())
                .status(dto.getStatus())
                .build();
    }

    public static List<OrderProductDto> toOrderProductDto(Orders orders) {
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for (OrderProduct orderproduct: orders.getOrderProduct()) {
            OrderProductDto dto = new OrderProductDto();
            dto.setProductId(orderproduct.getProduct().getId());
            dto.setCount(orderproduct.getCount());
            orderProductDtos.add(dto);
        }
        return orderProductDtos;
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
                .orderProduct(existingOrder.getOrderProduct())  // 기존 상품 정보 유지
                .address(dto.getAddress())  // 수정된 값
                .post(dto.getPost())  // 수정된 값
                .status(dto.getStatus())  // 수정된 값
                .time(existingOrder.getTime())  // 기존 시간 유지
                .build();
    }
}
