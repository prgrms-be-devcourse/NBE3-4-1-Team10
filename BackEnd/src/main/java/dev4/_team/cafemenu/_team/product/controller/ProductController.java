package dev4._team.cafemenu._team.product.controller;

import dev4._team.cafemenu._team.global.rs.RsData;
import dev4._team.cafemenu._team.product.dto.ProductDto;
import dev4._team.cafemenu._team.product.entity.Product;
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
    public List<ProductDto> showProducts() {

        return this.productService
                .findAll()
                .stream()
                .map(ProductDto :: new)
                .toList();
    }


    @PostMapping
    public RsData<ProductDto> createProduct(
            @RequestBody @Valid ProductForm productForm
            ) {

        Product product = this.productService.create(
                productForm.getPrice(),
                productForm.getName(),
                productForm.getImageUrl(),
                productForm.getType(),
                productForm.getContent()
        );

        return new RsData<>(
                "200",
                "상품이 생성되었습니다.",
                new ProductDto(product)
        );
    }





}
