package dev4._team.cafemenu._team.order.dto;

import dev4._team.cafemenu._team.orderProduct.dto.OrderProductDto;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductResponseDto;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private Long orderID;

    private String email;

    private String address;

    private String post;

    private String status;

    private int totalPrice;

    private List<OrderProductResponseDto> orderProduct;

}
