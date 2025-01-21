package dev4._team.cafemenu._team.orderProduct.dto;

import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductResponseDto {
    private String productName; // 상품 이름

    private int quantity;       // 수량

    private int price;          // 상품 가격

    public static OrderProductResponseDto of(OrderProduct orderProduct) {
        return OrderProductResponseDto.builder()
                .price(orderProduct.getPrice())
                .productName(orderProduct.getProduct().getName())
                .quantity(orderProduct.getCount())
                .build();
    }
}
