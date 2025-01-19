package dev4._team.cafemenu._team.delivery.service;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.mapper.OrderMapper;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeliveryScheduler {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateOrderStatus() {
        List<OrderResponseDto> orderDtos = OrderMapper.toDtoList(orderRepository.findByStatus("내일 배송"));
        for (OrderResponseDto orderDto : orderDtos) {
            User user = userRepository.findById(orderDto.getUserId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
            orderDto.setStatus("오늘 배송");
            Orders order = OrderMapper.toEntity(orderDto, user);
            orderRepository.save(order);
        }
    }
}
