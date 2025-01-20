package dev4.team.cafemenu.team.product.form;

import dev4.team.cafemenu.team.product.entity.Product;
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
