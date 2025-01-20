package dev4._team.cafemenu._team.orderProduct.repository;

import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
