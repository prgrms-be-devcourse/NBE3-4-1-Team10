package dev4._team.cafemenu._team.user.entity;

import dev4._team.cafemenu._team.global.BaseTimeEntity;
import dev4._team.cafemenu._team.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String authority;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Order> orderList;

}
