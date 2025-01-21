package dev4._team.cafemenu._team.order.dto;

import dev4._team.cafemenu._team.orderProduct.dto.OrderProductDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

    @NotEmpty
    @Size(max = 100, message = "주소는 100자를 초과할 수 없습니다.")
    private String address;

    @NotEmpty
    @Size(max = 10, message = "우편번호는 10자를 초과할 수 없습니다.")
    private String post;

    private List<OrderProductDto> orderProductDto;
}
