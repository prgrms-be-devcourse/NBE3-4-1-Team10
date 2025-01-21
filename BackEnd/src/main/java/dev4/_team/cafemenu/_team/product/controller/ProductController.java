package dev4._team.cafemenu._team.product.controller;

import dev4._team.cafemenu._team.global.rs.RsData;
import dev4._team.cafemenu._team.product.dto.ProductDto;
import dev4._team.cafemenu._team.product.form.ProductForm;
import dev4._team.cafemenu._team.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public RsData<List<ProductDto>> showProducts() {

        return new RsData<>(
                "200",
                "상품리스트를 조회했습니다.",
                productService.findDtoAll()
        );
    }

    @GetMapping("/product/{id}")
    public RsData<ProductDto> showProduct(
            @PathVariable("id") Long id
    ) {
        return new  RsData<>(
                "200",
                "상품을 조회했습니다.",
                productService.findDtoById(id)
        );


    }

    @PostMapping("/admin/product")
    public RsData<Void> createProduct(
            @RequestBody @Valid ProductForm productForm
    ) {
        productService.create(productForm);

        return new RsData<>(
                "204",
                "상품이 생성되었습니다."
        );
    }

    @PutMapping("/admin/product/{id}")
    public RsData<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductForm productForm
    ) {

        ProductDto productDto = productService.modify(id, productForm);

        return new RsData<>(
                "200",
                "상품이 수정되었습니다.",
                productDto
        );
    }

    @DeleteMapping("/admin/product//{id}")
    public RsData<Void> deleteProduct(
            @PathVariable("id") Long id
    ) {

        productService.delete(id);

        return new RsData<>(
                "200",
                "상품이 삭제되었습니다."
        );

    }




}
