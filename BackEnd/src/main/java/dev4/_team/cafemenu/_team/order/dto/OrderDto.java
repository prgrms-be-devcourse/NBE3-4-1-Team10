package dev4._team.cafemenu._team.order.dto;

import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Product product;

    @NotNull
    private User user;

    @NotBlank
    private String address;


    @NotBlank
    private String post;

    @NotNull
    private List<OrderProduct> orderProducts;
}
