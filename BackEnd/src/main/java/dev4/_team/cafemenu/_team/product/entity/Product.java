package dev4._team.cafemenu._team.product.entity;

import dev4._team.cafemenu._team.BaseTimeEntity;
import dev4._team.cafemenu._team.order.entity.Orders;
import dev4._team.cafemenu._team.product.form.ProductForm;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    private String name;

    private String imageUrl;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Orders> ordersList = new ArrayList<>();

    public Product modifyProduct(ProductForm productForm) {
        this.price = productForm.getPrice();
        this.name = productForm.getName();
        this.imageUrl = productForm.getImageUrl();
        this.type = productForm.getType();
        this.content = productForm.getContent();
        return this;
    }


}
