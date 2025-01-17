package dev4._team.cafemenu._team.product.controller;

import dev4._team.cafemenu._team.product.dto.ProductDto;
import dev4._team.cafemenu._team.product.form.ProductForm;
import dev4._team.cafemenu._team.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> showProducts() {

        return ResponseEntity.ok().body(productService.findDtoAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> showProduct(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(productService.findDtoById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody @Valid ProductForm productForm
    ) {
        productService.create(productForm);

        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductForm productForm
    ) {

        ProductDto productDto = productService.modify(id, productForm);

        return ResponseEntity.ok().body(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") Long id
    ) {

        productService.delete(id);

        return ResponseEntity.ok().build();

    }




}
