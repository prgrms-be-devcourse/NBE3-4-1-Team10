package dev4._team.cafemenu._team.order.repository;

import dev4._team.cafemenu._team.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findOrderById(Long id);
    List<Orders> findByUserId(Long id);
    List<Orders> findByStatus(String status);

}
