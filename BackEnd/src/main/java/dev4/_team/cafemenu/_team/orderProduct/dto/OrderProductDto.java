package dev4._team.cafemenu._team.orderProduct.dto;

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

}
