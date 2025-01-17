package dev4._team.cafemenu._team.order.dto;

import dev4._team.cafemenu._team.orderProduct.dto.OrderProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {

    private Long orderId;                // 주문 ID
    private String status;               // 주문 상태
    private LocalDateTime createdAt;     // 주문 생성 시간
    private Long userId;                 // 사용자 ID (선택 사항)
    private List<OrderProductResponseDto> products; // 주문된 상품 목록
    private int totalPrice;              // 총 결제 금액


}
