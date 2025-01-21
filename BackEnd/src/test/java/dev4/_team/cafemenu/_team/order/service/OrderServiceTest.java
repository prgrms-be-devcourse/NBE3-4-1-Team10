package dev4._team.cafemenu._team.order.service;

import dev4._team.cafemenu._team.order.dto.OrderDto;
import dev4._team.cafemenu._team.order.dto.OrderResponseDto;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.order.repository.OrderRepository;
import dev4._team.cafemenu._team.orderProduct.dto.OrderProductDto;
import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.product.repository.ProductRepository;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.entity.UserRole;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User testUser;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        // 사용자 생성
        testUser = User.builder()
                .email("testuser@example.com")
                .nickname("Test User")
                .password("password123") // 더미 비밀번호
                .role(UserRole.ROLE_USER)     // 적절한 사용자 역할 지정
                .build();
        userRepository.save(testUser);

        // 제품 생성
        testProduct = Product.builder()
                .name("Test Product")
                .price(1000)
                .build();
        productRepository.save(testProduct);
    }
    @Test
    void createOrder_success() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .address("123 Test Street")
                .post("12345")
                .orderProductDto(List.of(
                        OrderProductDto.builder()
                                .productId(testProduct.getId())
                                .count(2)
                                .build()
                ))
                .build();

        // When
        orderService.createOrder(orderDto, testUser.getId());

        // Then
        List<Orders> orders = orderRepository.findByUserId(testUser.getId());
        assertEquals(1, orders.size());
        Orders savedOrder = orders.get(0);
        assertEquals("123 Test Street", savedOrder.getAddress());
        assertEquals("12345", savedOrder.getPost());
        assertEquals(1, savedOrder.getOrderProducts().size());
    }

    @Test
    void getOrdersByUserId_success() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .address("123 Test Street")
                .post("12345")
                .orderProductDto(List.of(
                        OrderProductDto.builder()
                                .productId(testProduct.getId())
                                .count(2)
                                .build()
                ))
                .build();
        orderService.createOrder(orderDto, testUser.getId());

        // When
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(testUser.getId());

        // Then
        assertEquals(1, orders.size());
        assertEquals("123 Test Street", orders.get(0).getAddress());
    }

    @Test
    void deleteOrder_success() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .address("123 Test Street")
                .post("12345")
                .orderProductDto(List.of(
                        OrderProductDto.builder()
                                .productId(testProduct.getId())
                                .count(2)
                                .build()
                ))
                .build();
        orderService.createOrder(orderDto, testUser.getId());

        List<Orders> orders = orderRepository.findByUserId(testUser.getId());
        Long orderId = orders.get(0).getId();

        // When
        orderService.delete(orderId, testUser.getId());

        // Then
        List<Orders> remainingOrders = orderRepository.findByUserId(testUser.getId());
        assertTrue(remainingOrders.isEmpty());
    }

    @Test
    void updateOrder_success() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .address("123 Test Street")
                .post("12345")
                .orderProductDto(List.of(
                        OrderProductDto.builder()
                                .productId(testProduct.getId())
                                .count(2)
                                .build()
                ))
                .build();
        orderService.createOrder(orderDto, testUser.getId());

        List<Orders> orders = orderRepository.findByUserId(testUser.getId());
        Long orderId = orders.get(0).getId();

        OrderDto updateDto = OrderDto.builder()
                .address("Updated Address")
                .post("67890")
                .orderProductDto(List.of(
                        OrderProductDto.builder()
                                .productId(testProduct.getId())
                                .count(5)
                                .build()
                ))
                .build();

        // When
        orderService.updateOrder(orderId, updateDto, testUser.getId());

        // Then
        Orders updatedOrder = orderRepository.findById(orderId).orElseThrow();
        assertEquals("Updated Address", updatedOrder.getAddress());
        assertEquals("67890", updatedOrder.getPost());
        assertEquals(5, updatedOrder.getOrderProducts().get(0).getCount());
    }
}