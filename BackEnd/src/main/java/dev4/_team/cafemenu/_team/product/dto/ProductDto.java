package dev4._team.cafemenu._team.product.dto;

import dev4._team.cafemenu._team.product.entity.Product;
import lombok.Getter;

@Getter
public class ProductDto {

    private long id;

    private int price;

    private String name;

    private String imageUrl;

    private String type;

    private String content;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.price = product.getPrice();
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
        this.type = product.getType();
        this.content = product.getContent();
    }

}
