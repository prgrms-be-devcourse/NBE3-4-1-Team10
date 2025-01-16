package dev4._team.cafemenu._team.orderProduct.dto;

import dev4._team.cafemenu._team.order.entity.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderProductDto {

    @NotEmpty
    private int count;

    @NotEmpty
    private int price;

    @NotNull
    private Order order;
}
