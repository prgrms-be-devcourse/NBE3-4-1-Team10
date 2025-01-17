package dev4._team.cafemenu._team.product.form;

import dev4._team.cafemenu._team.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {

    private int price;

    @NotBlank(message = "이름은 필수항목입니다.")
    private String name;

    @NotBlank(message = "사진은 필수항목입니다.")
    private String imageUrl;

    @NotBlank(message = "종류는 필수항목입니다.")
    private String type;

    @NotBlank(message = "설명은 필수항목입니다.")
    private String content;

    // productform -> product로 받은 데이터를 엔티티 객체로 새로 만들기
    public Product toProductEntity() {
        return Product.builder()
                .price(price)
                .name(name)
                .imageUrl(imageUrl)
                .type(type)
                .content(content)
                .build();
    }

}
