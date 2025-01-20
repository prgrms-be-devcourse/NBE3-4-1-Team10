package dev4._team.cafemenu._team.product.dto;

import dev4._team.cafemenu._team.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductDto {

    private int price;

    private String name;

    private String imageUrl;

    private String type;

    private String content;

    public static ProductDto of(Product product) {
        return  ProductDto.builder()
                .price(product.getPrice())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
                .type(product.getType())
                .content(product.getContent())
                .build();
    }

}
