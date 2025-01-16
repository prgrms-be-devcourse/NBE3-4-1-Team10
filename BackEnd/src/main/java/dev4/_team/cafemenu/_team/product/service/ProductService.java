package dev4._team.cafemenu._team.product.service;

import dev4._team.cafemenu._team.product.entity.Product;
import dev4._team.cafemenu._team.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(int price, String name, String imageUrl, String type, String contents) {

        Product product = Product.builder()
                .price(price)
                .name(name)
                .imageUrl(imageUrl)
                .type(type)
                .content(contents)
                .build();

        return this.productRepository.save(product);
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

}
