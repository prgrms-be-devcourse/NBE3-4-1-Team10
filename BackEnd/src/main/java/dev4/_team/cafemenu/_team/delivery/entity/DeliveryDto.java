package dev4._team.cafemenu._team.delivery.entity;

import dev4._team.cafemenu._team.order.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryDto {

    private Long id;

    private boolean isDelivery;

    private Orders orders;
}
