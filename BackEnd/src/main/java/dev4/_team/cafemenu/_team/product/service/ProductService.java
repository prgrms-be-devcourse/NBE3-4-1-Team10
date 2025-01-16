package dev4._team.cafemenu._team.product.service;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.product.dto.ProductDto;
import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.product.form.ProductForm;
import dev4._team.cafemenu._team.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void create(ProductForm productForm) {

        Product product = productForm.toProductEntity();
        this.productRepository.save(product);
    }

    public List<ProductDto> findDtoAll() {
        List<Product> products = findAll();

        List<ProductDto> dtoProducts = products
                .stream()
                .map(ProductDto::of)
                .toList();
        log.info("상품DTO리스트를 조회했습니다.");

        return dtoProducts;
    }

    private List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        log.info("상품리스트를 조회했습니다.");

        return products;
    }

    private Product findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT)
        );
        log.info("상품을 조회했습니다.");

        return product;
    }


    public ProductDto findDtoById(Long id) {
        Product product = findById(id);

        ProductDto productDto = ProductDto.of(product);
        log.info("상품DTO를 조회했습니다.");

        return productDto;
    }

}
