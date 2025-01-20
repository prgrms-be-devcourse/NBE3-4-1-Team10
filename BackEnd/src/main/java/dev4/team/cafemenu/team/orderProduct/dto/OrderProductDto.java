package dev4.team.cafemenu.team.orderProduct.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductDto {

    @NotEmpty
    private Long productId;

    @NotEmpty
    private int count;

    @NotEmpty
    private int price;

}
