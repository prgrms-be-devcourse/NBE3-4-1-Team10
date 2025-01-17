package dev4._team.cafemenu._team.order.entity;

import dev4._team.cafemenu._team.global.BaseTimeEntity;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String address;

    private String post;

    private LocalDateTime time;

    private String status;

    @OneToMany
    private List<OrderProduct> orderProduct;
}
