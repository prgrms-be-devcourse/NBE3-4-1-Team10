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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Order> orderList;

    // 이메일, 닉네임, 비밀번호를 받아서 객체를 생성하는 생성자 추가
    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

}
