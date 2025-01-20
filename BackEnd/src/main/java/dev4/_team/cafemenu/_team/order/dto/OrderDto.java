package dev4._team.cafemenu._team.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

    @NotNull
    private Long orderId;

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 100, message = "주소는 100자를 초과할 수 없습니다.")
    private String address;

    @NotBlank
    @Size(max = 10, message = "우편번호는 10자를 초과할 수 없습니다.")
    private String post;


}
