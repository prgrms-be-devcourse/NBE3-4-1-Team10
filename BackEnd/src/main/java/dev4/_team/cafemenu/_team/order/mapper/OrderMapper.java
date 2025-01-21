package dev4._team.cafemenu._team.order.mapper;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
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
        List<OrderProduct> updatedOrderProducts = new ArrayList<>();

        for (OrderProductDto orderProductDto : dto.getOrderProductDto()) {
            OrderProduct existingOrderProduct = existingOrder.getOrderProduct().stream()
                    .filter(op -> op.getProduct().getId().equals(orderProductDto.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT));

            existingOrderProduct.updateCountAndPrice(orderProductDto.getCount());

            updatedOrderProducts.add(existingOrderProduct);
        }

        // 수정된 주문을 반영하여 새로운 Orders 엔티티 생성
        return Orders.builder()
                .id(existingOrder.getId())
                .user(existingOrder.getUser())  // 기존 사용자 정보 유지
                .orderProduct(updatedOrderProducts)  // 수정된 상품 정보 반영
                .address(dto.getAddress())  // 수정된 주소
                .post(dto.getPost())  // 수정된 우편번호
                .status(dto.getStatus())  // 수정된 상태
                .time(existingOrder.getTime())  // 기존 시간 유지
                .build();
    }


}
