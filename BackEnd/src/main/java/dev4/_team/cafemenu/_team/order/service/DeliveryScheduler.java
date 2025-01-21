package dev4._team.cafemenu._team.order.service;

import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeliveryScheduler {

    private final OrderRepository orderRepository;

    @Transactional
    @Scheduled(cron = "0 0 14 * * *") // 매일 오후 2시에 실행
    public void updateOrderStatus() {
        List<Orders> orders = orderRepository.findByStatus("배송 중");
        for (Orders order : orders) {
            order.updateDelivery();
        }
    }
}
