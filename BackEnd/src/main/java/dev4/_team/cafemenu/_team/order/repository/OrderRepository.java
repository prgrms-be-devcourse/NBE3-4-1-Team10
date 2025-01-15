package dev4._team.cafemenu._team.order.repository;

import dev4._team.cafemenu._team.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
