package dev4._team.cafemenu._team.orderProduct.dto;

import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.product.entity.Product;
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

    public OrderProduct toOrderProductEntity(Orders order, Product product) {
        return OrderProduct.builder()
                .product(product)
                .orders(order)
                .count(count)
                .price(product.getPrice() * count)
                .build();
    }
}
