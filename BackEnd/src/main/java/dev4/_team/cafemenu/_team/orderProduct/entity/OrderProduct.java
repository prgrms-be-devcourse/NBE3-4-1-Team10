package dev4._team.cafemenu._team.orderProduct.entity;

import dev4._team.cafemenu._team.global.BaseTimeEntity;
import dev4._team.cafemenu._team.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int count;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product")
    private Order order;
}