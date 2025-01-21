package dev4._team.cafemenu._team.order.entity;

import dev4._team.cafemenu._team.global.exception.BaseTimeEntity;
import dev4._team.cafemenu._team.orderProduct.entity.OrderProduct;
import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProduct;

    private String address;

    private String post;

    private LocalDateTime time;

    private String status;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void updateOrder(String address, String post) {
        this.address = address;
        this.post = post;
        this.status = LocalDateTime.now().getHour() >= 14 ? "내일 배송" : "배송 중";
    }

    public void updateDelivery() {
        this.status = "내일 배송";
    }
}
