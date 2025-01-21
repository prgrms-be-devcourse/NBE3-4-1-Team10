package dev4._team.cafemenu._team.orderProduct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductResponseDto {

    private Long productId;     // 상품 ID

    private String productName; // 상품 이름

    private int quantity;       // 수량

    private int price;          // 상품 가격
}
