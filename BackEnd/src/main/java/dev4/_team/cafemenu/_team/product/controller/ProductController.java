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
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public RsData<List<ProductDto>> showProducts() {

        return new RsData<>(
                "200",
                "상품리스트를 조회했습니다.",
                productService.findDtoAll()
        );
    }

    @GetMapping("/{id}")
    public RsData<ProductDto> showProduct(
            @PathVariable("id") Long id
    ) {
        return new  RsData<>(
                "200",
                "상품을 조회했습니다.",
                productService.findDtoById(id)
        );


    }

    @PostMapping
    public RsData<Void> createProduct(
            @RequestBody @Valid ProductForm productForm
    ) {
        productService.create(productForm);

        return new RsData<>(
                "204",
                "상품이 생성되었습니다."
        );
    }

    @PutMapping("/{id}")
    public RsData<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductForm productForm
    ) {

        return new RsData<>(
                "200",
                "상품이 수정되었습니다.",
                productService.modify(id, productForm)
        );




    }




}
