package dev4._team.cafemenu._team.product.controller;

import dev4._team.cafemenu._team.global.rs.RsData;
import dev4._team.cafemenu._team.product.dto.ProductDto;
import dev4._team.cafemenu._team.product.form.ProductForm;
import dev4._team.cafemenu._team.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;


    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> showProducts() {
        List<ProductDto> products = productService.findDtoAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> showProduct(
            @PathVariable("id") Long id
    ) {
        ProductDto productDto = productService.findDtoById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("/admin/product")
    public ResponseEntity<Void> createProduct(
            @RequestBody @Valid ProductForm productForm
    ) {
        productService.create(productForm);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductForm productForm
    ) {

        ProductDto productDto = productService.modify(id, productForm);

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
  
    @DeleteMapping("/admin/product//{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") Long id
    ) {

        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }




}
